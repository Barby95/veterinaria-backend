package com.demovete.veterinariabackend.repository;

import com.demovete.veterinariabackend.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//con Extends lo que hace es heredar todos los metodos que tiene JpaRepository
//Long es la Clave Primaria
// aqui podremos poner los filtros si tenes que buscar algo

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    //Esto filtra en la Base de datos. Asi no te trae todos los objetos.

    //SPRING DATa JPA por debajo tiene Hibernate(Jpa) que as su vez viene de (JDBC) --> H2/MYSQL/Postgresql
    List<Animal> findByEdad(Integer edad);

    List<Animal> findByEdadNotNull();
}