package com.demovete.veterinariabackend;

import com.demovete.veterinariabackend.model.Animal;
import com.demovete.veterinariabackend.model.Owner;
import com.demovete.veterinariabackend.model.catType;
import com.demovete.veterinariabackend.repository.AnimalRepository;
import com.demovete.veterinariabackend.repository.OwnerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Una clase con @Entity equivale a una table de base de datos
//Un objeto equivale a una fila en una tabla de base de datos
//RECORDEMOS las Interfaces son abstractas, no se inicializan. No podemos tener un new()
@SpringBootApplication
public class VeterinariaBackendApplication {

    public static void main(String[] args) {
        //Nos guardamos el Spring en una varuable y asi puedo pedirle algo.
        var context = SpringApplication.run(VeterinariaBackendApplication.class, args);
        //Esto normalemnte lo hariamos en un controlador o servicio

        //Estamos creando una variable(animalRepository) del tipo Animal Repository
        //Necesitamos tener una variable declarada, que no sea null porque si no no nos guardaria nada,
        // inyeccion de dependencia (Luego lo vemos)
        //Le dice a Spring que nos de
        //Spring le llama Bean a los OBJETOS en memoria

        //Esto obtiene los repositorios para poder hacer operaciones de base de datos con ellos.
        AnimalRepository animalRepository = context.getBean(AnimalRepository.class);
        OwnerRepository ownerRepository = context.getBean(OwnerRepository.class);


        // Podemos crear un animal: new
        //Tiene que tener el constructor para poder pasarle valores.
        Animal objCloe = new Animal("Cloe", 7, 4.5);
        Animal objBlackito = new Animal("Blackito", 10, 3.2);
        // Guardar el animal en base de datos usando el repositorio: .save(), va estar vacio de momento
        //Esto lo escribimos asi en java pero lo traduce a SQL en la terminal veremos un INSERT

        //objCloe.setName("Cloe");
        //objCloe.setColor("Gris");
        //objCloe.setPeso(4.5);
        //objCloe.setEdad(7);
        //objCloe.setEspecie("Gato");
        //objCloe.setRaza("Europeo");
        //objCloe.setGenero("Hembra");


        animalRepository.save(objCloe);
        animalRepository.save(objBlackito);
        //Si queremos hacer varios objetos e insertarlos seria con el  metodo .saveAll()

        Owner objOwner = new Owner(null,"Barbara", "Urbano", 30, "123456H");
        Owner objOwner2 = new Owner(null,"Patricia", "Moreno", 53, "654321F");
        //ownerRepository.save(objOwner);
        //ownerRepository.save(objOwner2);

        //Para mirar los datos por la terminal
        //System.out.println("Animal guardado en base de datos: " + objCloe);
        //System.out.println("Owner guardado en base de datos: " + objOwner);


        //obtener todos los animales de base de datos que equivale a SELECT * from animal, con finAll()
        //animalRepository.findAll(); si lo dejo asi no lo estoy guardando en una variable, si quiero imprimirlos
        //voy a tener que guardarlo (forEach es un bucle for, que le pasa una funcion a otra funcion, lo veremos despues)

        //De momento lo haremos diferente.
        List<Animal> animals = animalRepository.findAll();
        //Esto lo sacaremos por el HTML, ahora lo estamos viendo aqui.
        //System.out.println(animals); cuando usabamos array veiamos el tamanio con .lenght(), pero en las
        //listas es con .size()

//        for(int i = 0; i < animals.size(); i++){
//            //como es una lista no va con corchetes, si no que usa un get()
//            System.out.println(animals.get(i));
//        }
        // esta es la mejor forma. Le dices que quieres iterar la lista de animales uno por uno.


        //se llama bucle forEach
        for(Animal animal : animals){
            System.out.println(animal);
        }

        //Imprimir los animales obtenidos con un bucle for.


        //List<Owner> owners = ownerRepository.findAll();
        //System.out.println(owners);

        //for(Owner owner : owners){
          //  System.out.println(owner);
        //}

        //Ponemos el nombre del repositorio y ejecutamos el metodo, tenemos que pasarle animales
        // entonces tenemos que crearlo, hay que crear una Lista

        //Animal cloe1 = new Animal("CLOOE", 7, 4.5);
        //Animal blackito1 = new Animal("BLACKIIITO", 10, 3.2);
        // Esta es la opcion clasica
        //List<Animal> animals1 = new ArrayList<>();
        //animals1.add(cloe1);
        //animals1.add(blackito1);

        //animalRepository.saveAll(animals1);


        // Crear una lista vacia, a la lista hay que indicarle
        //de que tipo es, en este caos la lista es de TIPO Animal (es una CLASE), esto viene de Generic
        Animal cloe2 = new Animal("Cloeee", 8, 4.5);
        Animal blackito2 = new Animal("Blackitoooo", 11, 3.2);

        //opcion mas moderna, es una lista inmutable, si necesitamos ponerle algo nuevo
        //primero deberiamos crear otra lista y luego ponerle el contenido de la anterior y el nuevo contenido
        //Esto es por tema de no tener problema de ...
        List<Animal> animals2 = List.of(cloe2, blackito2);
        animalRepository.saveAll(animals2);

        //Probamos ahora el count(), cuenta las filas de una tabla
        Long cantidadAnimales = animalRepository.count();

        if(cantidadAnimales > 0){
            System.out.println("Hay animales en la base de datos " + cantidadAnimales);
        }
        else{
            System.out.println("No hay animales en la base de datos");
        }

        //eixstById devuelve boolean, 1L significa long, o 2L
        //animalRepository.existsById(1L);
        //System.out.println("Existe el animal con id 1? " + animalRepository.existsById(1L));

        long id = 1L;
        boolean existe = animalRepository.existsById(id);

        if(existe){
            System.out.println("El animal con id " + id + " existe");
        }
        else{
            System.out.println("El animal con id " + id + " no existe");
        }

        //deleteAll(), es de tipo void entonces no devuelve nada. Solo borra los datos de esta tabla, entidad
        //Si le hacemos un count() nos deberia dar cero.
        //animalRepository.deleteAll();
        //mejor delete from in bach


        //deleteById(), es similar al existbyid, te pide un identificador
        //animalRepository.deleteById(1L);
        //System.out.println("Animal borrado con id " + cloe2.getId());
        //animalRepository.deleteById(cloe2.getId());

        //delete
        //animalRepository.delete(cloe2);

        //Si se quiere borrar por nombre, requiere metodo personalizado en el repositorio
        //animalRepository.deleteByName(cloe2);

        //findById() Lo busca por id pero te trae to do el objeto.
        //Optional es una clase de java para que no rte devuelva null,
        //El problema de tratar con null es que puede saltar un nullExteption.

        //Long idABuscar = 2L;
        //Animal animalFromDataBase = animalRepository.findById(idABuscar);
        //Optional<Animal> animalFromDataBase = animalRepository.findById(idABuscar);

        //Animal animal2 = animalFromDataBase.get();
        //System.out.println(animal2);

        //Crear un restaurante espaniol, en este caso un animal EUROPEO. Esto tambien sirve para que los usuarios puedan FILTRAR.
        Animal anEuro = new Animal();
        anEuro.setCatType(catType.EUROPEO);
        animalRepository.save(anEuro); // Esto guardaria en la base de datos
        System.out.println(anEuro);


        //Crear un  restaurante de comida japonesa, en este caso crear un animal OCEANIA
        Animal anOcean = new Animal();
        anOcean.setCatType(catType.AMERICANO); // Si queremos agregarle mas atributos, con .set
        animalRepository.save(anOcean);
        System.out.println(anOcean);


        //Probar a intentar otro tipo de comida y ver que no deja , EN ESTE CASO otro tipo de gato Argentino
        //Animal anAsiatico = new Animal();
        //anAsiatico.setCatType(catType.ARGENTINIAN);

        //Probar fecha de starDate del restaurante, en este caso del animal
        Animal anEuropean1 = new Animal();
        anEuropean1.setFechaAdopcion(LocalDate.now());//para pasarle la fecha de hoy
        animalRepository.save(anEuropean1);

        //Fecha futura debemos marcar unas fechas concretas
        Animal anEuropean2 = new Animal();
        anEuropean2.setName("Rubi");
        anEuropean2.setFechaAdopcion(LocalDate.of(2014, 7, 12));

        //MANY TO One - Asociar un restaurante a dos empleados -- En este caso seria asociar 1 owner a 2 animales
        //paso 1. Crear restaurante y guardarlo en BD - Crear 1 owner y guardarlo en BD
        //paso 2. Crear empleados, setRestaurant y guardar. (con ese restaurante) - Crear animales, asociarlo a ese owner y guardar

        Owner ownerAsociacion = new Owner();
        ownerAsociacion.setFirstNombre("Patricia");
        ownerRepository.save(ownerAsociacion);

        Animal animalAsociacion1 = new Animal();
        animalAsociacion1.setName("Khloe");
        animalAsociacion1.setFechaAdopcion(LocalDate.of(2018, 11, 15));
        animalAsociacion1.setOwner(ownerAsociacion); // aqui lo estamos asociando a ese owner que acabamos de crear
        animalRepository.save(animalAsociacion1);
        System.out.println(animalAsociacion1);

        Animal animalAsociacion2 = new Animal();
        animalAsociacion2.setName("Nina");
        animalAsociacion2.setFechaAdopcion(LocalDate.of(2025, 5, 19));
        animalAsociacion2.setOwner(ownerAsociacion); //aqui lo estamos asociando a ese owner que acabamos de crear
        //Aqui lo estamos guardando en la BD
        animalRepository.save(animalAsociacion2);
        System.out.println(animalAsociacion2);

        // Buble for para iterar sobre todos los empleados, imprimiendo el nombre del empleado y el nombre de su restaurante
        //Seria iterar sobre los animales, imprimiendo el nombre del animal y el nombre del owner

        //Como lo hizo el profe con una lista, imprimir owner de animales de forma SEGURA sin null pointer excepticion
        //List<Animal> animales = animalRepository.findAll();
        //for (Animal animal : animales) {
          //  System.out.println("Nombre animal: " + animal.getName() + "Nombre del owner: " + (animal.getOwner() != null ? animal.getOwner().getFirstNombre() : "No tiene owner"));
            //if(animal.getFechaAdopcion() != null){
              //  System.out.println("Trabaja en: " + animal.getOwner().getFirstNombre());
            //}
            //else{
              //  System.out.println("No trabaja en ningun restaurante");
            //}
        //}

        //con findAll
        for (Animal animal : animalRepository.findAll()) {
            System.out.println(
                    "Nombre animal: " + animal.getName() +
                            "Nombre del owner: " + (animal.getOwner() != null ? animal.getOwner().getFirstNombre() : "No tiene owner"));
        }

        //Probar a filtrar por edad pero que no sea null
        List<Animal> animalesEspecie = animalRepository.findByEdadNotNull();
        System.out.println(animalesEspecie);
    }

}
