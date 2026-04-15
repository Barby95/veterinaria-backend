package com.demovete.veterinariabackend.repository;
//asegurarse que se importante estos
import com.demovete.veterinariabackend.model.Owner;
import com.demovete.veterinariabackend.model.OwnerLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Aqui se pueden poner consultas personalizadas
public interface OwnerRepository extends JpaRepository<Owner, Long> {

}

