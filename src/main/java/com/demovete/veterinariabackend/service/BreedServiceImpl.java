package com.demovete.veterinariabackend.service;

import com.demovete.veterinariabackend.dto.BreedCreateRequestDTO;
import com.demovete.veterinariabackend.dto.BreedResponseDTO;
import com.demovete.veterinariabackend.dto.BreedUpdateRequestDTO;
import com.demovete.veterinariabackend.model.Breed;
import com.demovete.veterinariabackend.model.Species;
import com.demovete.veterinariabackend.repository.BreedRepository;
import com.demovete.veterinariabackend.repository.PetRepository;
import com.demovete.veterinariabackend.repository.SpeciesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BreedServiceImpl implements BreedService {
    private final BreedRepository breedRepository;
    private final SpeciesRepository speciesRepository;
    private final PetRepository petRepository;

    @Override
    @Transactional
    public BreedResponseDTO createBreed(BreedCreateRequestDTO dto) {
        //Primero tenemos la especie
        Species species = speciesRepository.findById(dto.speciesId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No se encuentra la especie con ID: " + dto.speciesId()));

        //Verificamos  que la raza para esa especie no exista
        if (breedRepository.existsByNameIgnoreCaseAndSpeciesId(dto.name(), dto.speciesId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Ya existe la raza '" + dto.name() + "' en esa especie");
        }

        Breed breed = Breed.builder()
                .name(dto.name())
                .species(species)
                .build();

        return mapToDto(breedRepository.save(breed));
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public BreedResponseDTO getBreedById(Long id) {
        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No se encuentra la raza con ID: " + id));
        return mapToDto(breed);
    }

    @Override
    public List<BreedResponseDTO> getAllBreeds() {
        return breedRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<BreedResponseDTO> getBreedsBySpeciesId(Long speciesId) {
        return breedRepository.findBySpeciesIdOrderByNameAsc(speciesId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    @Transactional
    public BreedResponseDTO updateBreed(Long id, BreedUpdateRequestDTO dto) {
        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No se encuentra la raza con ID: " + id));

        // Verificar duplicado
        if (!breed.getName().equalsIgnoreCase(dto.name())
                && breedRepository.existsByNameIgnoreCaseAndSpeciesId(dto.name(), breed.getSpecies().getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Ya existe la raza '" + dto.name() + "' en esa especie");
        }

        breed.setName(dto.name());
        return mapToDto(breedRepository.save(breed));
    }

    @Override
    @Transactional
    public void deleteBreed(Long id) {
        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No se encuentra la raza con ID: " + id));

        if (petRepository.existsByBreedId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No se puede borrar: hay mascotas con esta raza");
        }

        breedRepository.delete(breed);
    }

    private BreedResponseDTO mapToDto(Breed breed) {
        return new BreedResponseDTO(
                breed.getId(),
                breed.getName(),
                breed.getSpecies().getId(),
                breed.getSpecies().getName()
        );

    }
}

