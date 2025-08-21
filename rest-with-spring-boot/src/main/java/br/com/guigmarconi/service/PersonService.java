package br.com.guigmarconi.service;

import br.com.guigmarconi.exception.ResourceNotFoundException;
import br.com.guigmarconi.model.Person;
import br.com.guigmarconi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    PersonRepository personRepository;
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findById(Long id){
        logger.info("Finding one Person!");

        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
    }

    public List<Person> findAll(){
        logger.info("Finding them all!");

        return personRepository.findAll();
    }

    public Person createPerson(Person person){
        logger.info("Creating one Person!");
        return personRepository.save(person);
    }

    public Person updatePerson(Person person){
        logger.info("Updating one Person!");
        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(entity);
    }

    public void delete(Long id){
        logger.info("Deleting one Person!");

        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));

        personRepository.delete(entity);
    }

}
