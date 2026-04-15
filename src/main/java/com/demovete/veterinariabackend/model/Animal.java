package com.demovete.veterinariabackend.model;
//Estos se importan cuando hemos puesto @Entity y @Id (no llevan ; al final porque son anotaciones)
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.swing.text.StyledEditorKit;
import java.time.LocalDate;
//Se importan todos los paquetes import jakarta.persistence.*; y me borraria el de .Id, .GeneratedValue..


/*
SELECT * FROM ANIMAL;
INSERT INTO animal (ID, NOMBRE, RAZA, ESPECIE) VALUES (2, 'BLACKITO', 'COMUN', 'CONEJO')
UPDATE animal SET ESPECIE='GATO' WHERE id=1
UPDATE animal SET GENERO='HEMBRA' WHERE id=1
UPDATE animal SET GENERO='MACHO' WHERE id=2
 */

//EN TESTING se anadio las anotaciones de getter y setter pero tenemos que tener la dependencia

@Entity
//Por si le queremos cambiar el nombre de la tabla.
@Table(name = "ANIMALES")
@Builder
@AllArgsConstructor
public class Animal {
    //Tiene mayor capacidad de almacenamiento el LONG.
    @Id
    //Para generar automaticamante el ID, se puede configurar la estrategia esta es la opcion mas comun
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //En proyectos grandes puede ser un UUID que son numeros grandes
    private Long id;
    //Si necesitamos que sea unico el nombre es @Column(unique) CTRL+espacio te da las opciones

    private String name;
    //Si quisieramos que tenga un valor por defecto usamos @Column(columnDefinition="BOOLEAN DEFAULT true")
    //@Column(columnDefinition="BOOLEAN DEFAULT true")
    private String especie;
    private Integer edad;
    private String raza;
    private String color;
    private String genero;
    private Double peso;
    @Column(name = "active")
    private Boolean active = true;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    //ToString.Exclude hace que no te lo incluya en el toString ya que puedes tener problema de ciclos, por la cantidad de objetos que te trae.
    @ManyToOne // Se crearon los getter y setter tambien.
    @JoinColumn // tambien te deja cambiar  la columna de las clave foranea pero NO ES NECESARIA
    private Owner owner;

    //vamos agregar enum y fecha de adopcion, tipo de
    //LocalDate(java.time) te tiene en cuenta el calendario, puede aniadirle dias, anios viciestos
    //Podemos ponerle una fecha por defecto con  = LocalDate.now()
    //@CreationTimestamp Esta anotacion es para que la BD genere la fecha, util para registrar
    //fecha automaticamente sin preocuparse de tener que cambiarla
    private LocalDate fechaAdopcion = LocalDate.now(); // con el.of() para pasarle una fecha concreta


    @Enumerated(EnumType.STRING)
    //@Column(name = "cat_type") este no e snecesario
    @Column(columnDefinition = "ENUM('AMERICANO', 'EUROPEO') DEFAULT 'EUROPEO'")
    private catType catType;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public catType getCatType() {
        return catType;
    }

    public void setCatType(catType catType) {
        this.catType = catType;
    }
//Asociacion entre dos tablas. Asociacion muchos a uno, mas comun y utilizada
    //@ManyToOne
    //private Owner owner;

    //Debemos tener si o si un constructor vacio para que se pueda crear un objeto de esta clase.
    public Animal() {
    }
    //Esto es otro un constructor
    public Animal(String name, Integer edad, Double peso) {
        this.name = name;
        this.edad = edad;
        this.peso = peso;
    }

    //Aqui tenemos los metodos getters y setters(get permite obtener valor para consultarlo y set permite modificar valor)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public LocalDate getFechaAdopcion() {
        return fechaAdopcion;
    }

    public void setFechaAdopcion(LocalDate fechaAdopcion) {
        this.fechaAdopcion = fechaAdopcion;
    }


    //el metodo toString() te deja mirar en la terminal el valor que tiene los atributos. Si no sale la referencia en memoria
    // este se genero automaicamente es para indicarle que quieres heredar de una clase y sobre escribir metodos.

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", especie='" + especie + '\'' +
                ", edad=" + edad +
                ", raza='" + raza + '\'' +
                ", color='" + color + '\'' +
                ", genero='" + genero + '\'' +
                ", peso=" + peso +
                ", active=" + active +
                ", fechaAdopcion=" + fechaAdopcion +
                ", catType=" + catType +
                '}';
    }


//Nos faltan ver las asociaciones - relaciones entre tablas
    //(esto se hace entre las claves primarias y foraneas), Enum, Agregar fechas
}
