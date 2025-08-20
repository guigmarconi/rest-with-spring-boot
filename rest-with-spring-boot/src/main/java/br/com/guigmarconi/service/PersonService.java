package br.com.guigmarconi.service;

import br.com.guigmarconi.model.Person;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findById(String id){
        logger.info("Finding one Person!");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Michael");
        person.setLastName("Cane");
        person.setAddress("São Caetano do Sul - São Paulo - Brasil");
        person.setGender("Male");
        return person;
    }

    public List<Person> findAll(){
        logger.info("Finding them all!");

        List<Person> persons = new ArrayList<Person>();

        for (int i=0; i < 8 ; i++){
            Person person = mockPerson(i);
            persons.add(person);
        }

        return persons;
    }

    public Person createPerson(Person person){
        logger.info("Creating one Person!");
        return person;
    }

    public Person updatePerson(Person person){
        logger.info("Updating one Person!");
        return person;
    }

    public void delete(String id){
        logger.info("Deleting one Person!");
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Michael" + i);
        person.setLastName("Cane" + i);
        person.setAddress("Some address - Brasil");
        person.setGender("Male");
        return person;
    }


}
