package com.demovete.veterinariabackend.repository;

import com.demovete.veterinariabackend.model.Animal;
import com.demovete.veterinariabackend.model.OwnerLevel;
import com.demovete.veterinariabackend.model.catType;
import lombok.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
// aqui hay que agregar el DataJpaTest para qu sea un test, porque es una anotacion de spring que es un framework
//lo vemos en las librerias externas
//cada uno de estos test son transaccionales se supone que cuando acaba el test desace los cambios hechos para no afectar otros.
@DataJpaTest
@Builder
@NoArgsConstructor
@AllArgsConstructor

class AnimalRepositoryTest {

    //Tenemos que crear el objeto que queremos testear
    @Autowired
    AnimalRepository repository;//Se queda null, sin inicializar y si lo usaas da un nullpointexcepcion

    Animal animal1;
    Animal animal2;

    @AfterAll
    static void afterAll() {
        //Esto se ejecuta despues de la clase y podriamos cerrar la base de datos, liberar recursos de memoria, JUnit te dan 4: beforEach, AfterAll y . . . .
    }

    // se ejecuta antes de cada test. Crea un animal y ese se crea antes del comienzo del test save, saveAll
    @BeforeEach
    void setUp() {
        //crear datos de pruebas para usar en los test
        //Crea las variables, luego las rellena y luego las usa, eso en proyecto biblioteca
        //Se crea el escenario de prueba fixture
        //inicializar datos para el test
        System.out.println("Ejecutando before each");
        //Esto es con el constructor o lo podemos hacer con el Builder pero para que funcione hay que ponerle una anotacion a la clase @Builder y te pide constructor

        animal1 = new Animal();
        //Builder me deja ponerle las variables que necesite, el constructor debo ponerlas a todas y en orden
        animal2 = Animal.builder().color("gris").build();
        //Para guardar en la base de datos:, podemos guardarlo en el metodo save()
        repository.save(animal1);

    }

    //Es importante en cada test tener un assert
    @Test
    void count(){
        assertEquals(2, repository.count());
    }

    @Test
    void existsById() {
        assertTrue(repository.existsById(animal1.getId()));
    }

    @Test
    void save() {
        //pruebas a guardar con los datos ya cargados
        //Crear un nuevo animal, si se le ha creado un id es que fue a BD.
        Animal animal = new Animal();
        animal.setName("Maximo");
        repository.save(animal);
        assertNotNull(animal.getId()); // Se le asigna automaticamente un id
        //Podemos probar un existByID que te devuelve el boleean
        assertTrue(repository.existsById(animal.getId()));

    }

    @Test
    void saveAll() {
        //Creamos una lista que puede ser de la manera clasica, mutable que puede agregar elementos con el new
        //Lista de nombres
        //String[] nombres = new String[] {"barbie","santi","patricia"} es un array de nombres pero fijo
        List<String> nombres = new ArrayList<>();
        nombres.add("Ceci");
        nombres.add("Maximito");

        Animal animal65 = new Animal();
        Animal animal66 = new Animal();
        List<Animal> animales = new ArrayList<>();
        animales.add(animal65);
        animales.add(animal66);
        //Podemos pulsar CTRL + Click y te dice donde se creo
        repository.saveAll(animales);
        assertEquals(4, repository.count()); // deberia haber 4 porque en el setUp creamos 2

        //Algo habitual es crear un mapa que la clave es un string y el valor sea una lista
        //Este mapa es un diccionario Map<String, List<Animal>> map = new HashMap();

    }
    //En TESTING es util saber depurar.
    @Test
    void findById() {
        //Optional es para NO tratar con null de manera directa,
        Optional<Animal> animalOptional = repository.findById(animal1.getId());
        assertTrue(animalOptional.isPresent());

        Animal animal = animalOptional.get();
        assertEquals("gris", animal.getColor());
    }

    @Test
    void findAll() {
        //Animal animales = repository.findAll();
        //no haria falta este
        //assertNotNull(animales);
        //assertEquals(2, animales.size());
        //assertTrue(animales.size()>= 2);
    }

    @Test
    void deleteById(){
        //Borrar un animal por su id
        //Borrar el animal 1

        //Comprobar que SI existe el animal 1, con el isExist(), aqui puedo hacer un count y en el otro assert que sea ese numero -1
        assertTrue(repository.existsById(1L));
        long numeroAnimalesAntes = repository.count();
        //Borrarlo: si queremos borrar con el objeto priemro debemos recuperar el objeto
        repository.deleteById(1L); // es void no devuelve nada asique deberiamos hacer un count o un existById de nuevo.

        //comprobar que NO existe el animal 1
        assertFalse(repository.existsById(1L));
        long numeroAnimalesDespues = repository.count();
        assertEquals(numeroAnimalesAntes - 1, numeroAnimalesDespues);

    }
    @Test
    //tambien tengo que hacer estos test en el owner
    //falta el test de campoFecha
    void catType(){
        //Europeo
        Animal animal = new Animal();
        animal.setCatType(catType.EUROPEO);
        Animal animalGuardado = repository.save(animal);
        assertNotNull(animalGuardado.getCatType()); //compruebo que level no es null
        assertEquals(catType.EUROPEO, animalGuardado.getCatType()); // compruebo que el level es JUNIOR

        //Americano
        //VERIFICAMOS QUE SE LE ASIGNA UN VALOR POR DEFECTO QUE DEBERIA SER JUNIOR/europeo
        Animal animalAmericano = new Animal(); // porque por defecto es americano
        Animal americanoGuardado = repository.save(animalAmericano);
        //pROBAR A QUITAR LO QUE SEA SENIOR POR DEFECTO PARA VER COMO FALLA LA COMPARACION
        assertEquals(catType.AMERICANO, americanoGuardado.getCatType());
    }
    @Test
    void startDate(){
        //Probar fecha por defecto que se asigna la fecha actual
        //luego probar cambiar la fecha y ver si funciona.
    }


}