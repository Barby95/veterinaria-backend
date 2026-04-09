package com.demovete.veterinariabackend.repository;

import com.demovete.veterinariabackend.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}