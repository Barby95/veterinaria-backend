package com.demovete.veterinariabackend.repository;

import com.demovete.veterinariabackend.model.Animal;
import com.demovete.veterinariabackend.model.Owner; // Este se importo para usarlo  en el test void manyToOne
import com.demovete.veterinariabackend.model.OwnerLevel;
import com.demovete.veterinariabackend.model.catType;
import jakarta.persistence.EntityManager;
import lombok.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.awt.datatransfer.MimeTypeParameterList;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
// aqui hay que agregar el DataJpaTest para qu sea un test, porque es una anotacion de spring que es un framework
//lo vemos en las librerias externas
//cada uno de estos test son transaccionales se supone que cuando acaba el test desace los cambios hechos para no afectar otros.
@DataJpaTest
//@Builder //QUITAMOS ESTO PARA QUE FUNCIONE EL TEST
//@NoArgsConstructor //QUITAMOS ESTO PARA QUE FUNCIONE EL TEST
//@AllArgsConstructor //QUITAMOS ESTO PARA QUE FUNCIONE EL TEST

class AnimalRepositoryTest {

    //Tenemos que crear el objeto que queremos testear
    @Autowired
    AnimalRepository repository;//Se queda null, sin inicializar y si lo usaas da un nullpointexcepcion

    Animal animal1;
    Animal animal2;

    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    EntityManager entityManager; // Esto lo tenemos que conocer, es es gestor de entidades guardarla recuperarla limpiar la memoria

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
        animal1.setName("Maximo");
        animal1.setColor("Amarillo");
        animal1.setActive(true);
        repository.save(animal1);
        //Builder me deja ponerle las variables que necesite, el constructor debo ponerlas a todas y en orden

        animal2 = Animal.builder().color("Gris").active(true).name("Pichu").build();
        //Para guardar en la base de datos:, podemos guardarlo en el metodo save()
        repository.save(animal2); // FALTABA ESTO
    }

    //Es importante en cada test tener un assert
    @Test
    void count(){
        assertEquals(2, repository.count());
    } //AHORA TENGO 2 ANIMALES DEBERIA FUNCIONAR

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
    //ESTE FALLABA PORQUE ESTABA MAL MI SETUP()
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
        Optional<Animal> animalOptional = repository.findById(animal2.getId()); //HABIA PUESTO ANIMAL1. PERO LO CREE SIN COLOR, ERA ANIMAL2 - TIENE COLOR GRIS
        assertTrue(animalOptional.isPresent());

        Animal animal = animalOptional.get();
        assertEquals("gris", animal.getColor());
    }

    @Test
    void findAll() {
        //Animal animales = repository.findAll();
        List<Animal> animales = repository.findAll();
        //no haria falta este
        assertNotNull(animales);
        //assertEquals(2, animales.size());
        assertTrue(animales.size()>= 2);
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
        //Crear objeto Empleado con constructor o builder
        //Given - dado un animal

        Animal animal14 = new Animal();
        //var animal = Animal.builder().name("Maximo").build(); si lo usamos debemos ponerle todos los atributos porque si no los setea a null los que no les pasemos
        animal14.setName("Maximo"); // Tenemos que comprobar que la fecha de asigna automaticamente si tiene el localDate.now()
        //When
        Animal animalGuardado = repository.save(animal14);
        //Luego hacer un assert startDate not null

        //Then
        assertNotNull(animalGuardado.getFechaAdopcion());
        //Comparar la fecha del animal con la fecha de ahora
        assertEquals(LocalDate.now(), animalGuardado.getFechaAdopcion());

        //luego probar cambiar la fecha y guardarla y ver si funciona. Esto podria ser otro test distinto.
        animal14.setFechaAdopcion(LocalDate.of(2024, 2, 19));
        Animal animalGuardado2 = repository.save(animal14);
        assertNotNull(animalGuardado2.getFechaAdopcion());
    }
    @Test
    void manyToOneAnimalTest(){
        //paso 1. Crear un owner y guardarlo, problema que no lo podemos guardar asi debemos importar el repositorio de restaurante
        Owner owner = new Owner();
        owner.setFirstNombre("Sebastian");
        //Necesitamos el correspondiente, entonces hay que importarlo y usarlo
        ownerRepository.save(owner);

        //paso 2. crear un animal y asociarle el owner
        Animal animal = new Animal();
        animal.setName("Maximo");
        animal.setOwner(owner);// Le estamos pasando un objeto y ese objeto tiene que estar creado en la BD.
        //Ahora lo guardamos en la BD
        Animal animalGuardado = animalRepository.save(animal);

        //Hacemos un assert para ver que el animal no es nullo y
        assertNotNull(animalGuardado.getOwner());
        //Optional<Animal> animalOptional =  repository.findById(animalGuardado.getId());
        Animal animalFromDB = repository.findById(animalGuardado.getId()).get();
        assertNotNull(animalFromDB.getOwner());

    }

    //Filtros basicos
    //Filtrar empleados por nivel JUNIOR repository.FindLevel() crearlo en la interfaz
    @Test
    void findByLevel(){
        //repository.deleteAll(); //borramos todos los 'empleados' para ver que el test funciona bien
        //repository.save(Animal.builder().name("Connie").level(OwnerLevel.JUNIOR).build());
        //repository.save(Animal.builder().name("Coco").level(OwnerLevel.JUNIOR).build());
        //repository.save(Animal.builder().name("Gara").level(OwnerLevel.CRACK).build());

        //List<Animal> juniors = repository.findAllByLevel(OwnerLevel.JUNIOR);
        //assertEquals(2, juniors.size()); // Comprueba que en la lista de Juniors hay 2 elementos.
    }

    //Filtrar empleados que esten active = true y trabajen en dominiosPizz hay que ponerle un AND


    @Test
    void findByActiveAndOwner() {
        //forma de crearlo en 1 linea con el builder
        //Lo bueno del builder es que podemos ponerle los atributos en cualquier orden.
        //ownerRepository.save(Owner.builder().firstNombre("Sebastian").active(true).build());

        repository.deleteAll();
        //paso 1. Crear dos owner
        Owner owner1 = new Owner();
        owner1.setFirstNombre("Ferrari");
        ownerRepository.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstNombre("Redbull racing");
        ownerRepository.save(owner2);

        //paso 2. Crear cuatro animals, dos por owner

        Animal animal1 = animalRepository.save(Animal.builder().name("Hamilton").active(true).owner(owner1).build());

        Animal animal2 = animalRepository.save(Animal.builder().name("Leclerc").active(true).owner(owner1).build());

        Animal animal3 = animalRepository.save(Animal.builder().name("Hadjar").active(false).owner(owner2).build());

        Animal animal4 = animalRepository.save(Animal.builder().name("Verstappen").active(true).owner(owner2).build());

        //paso 3. Invocar el nuevo metodo findAllby.. ESTO ES LO IMPORTANTE, el resto son datos demo
        List<Animal> listaAnimales = repository.findAllByActiveTrueAndAnimalName("Ferrari");

        //paso 4. assert
        //MimeTypeParameterList animalesActivosDeFerrari;
        //assertEquals(3, animalesActivosDeFerrari.size());
    }
}