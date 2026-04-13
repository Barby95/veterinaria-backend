package com.demovete.veterinariabackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "owner")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    //Hacemos que sea independiente el ID a datos del usuario,
    //el dni podria cambiar y entonces deberiamos actualizarlo en todos lados

    private Long id;
    private String firstNombre;
    private String lastName;
    private Integer age;
    private String dni;
    //@Enumerated(EnumType.STRING)
    //private OwnerLevel level = OwnerLevel.JUNIOR; // Esto es para que por defecto el nuevo owner es junior

    //Creamos el constructor vario y el que inserte datos
    public Owner() {

    }

    public Owner(Long id, String firstNombre, String lastName, Integer age, String dni) {
        this.id = id;
        this.firstNombre = firstNombre;
        this.lastName = lastName;
        this.age = age;
        this.dni = dni;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstNombre() {
        return firstNombre;
    }

    public void setFirstNombre(String firstNombre) {
        this.firstNombre = firstNombre;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", firstNombre='" + firstNombre + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", dni='" + dni + '\'' +
                '}';
    }

    //Estos son los getters y setters, y metodos que tambien veremos luego.
   // public Long getId() {return id;}

    //public void setId(Long id) {this.id = id;}
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('JUNIOR', 'SENIOR') DEFAULT 'JUNIOR")
    private OwnerLevel ownerLevel;

    public OwnerLevel getOwnerLevel() {
        return ownerLevel;
    }

    public void setOwnerLevel(OwnerLevel ownerLevel) {
        this.ownerLevel = ownerLevel;
    }

    //TODO [Reverse Engineering] generate columns from DB
}
