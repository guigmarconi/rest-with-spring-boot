package br.com.guigmarconi.service;

import br.com.guigmarconi.controllers.TestLogController;
import br.com.guigmarconi.data.dto.PersonDTO;
import br.com.guigmarconi.exception.ResourceNotFoundException;
import static br.com.guigmarconi.mapper.ObjectMapper.parseListObject;
import static br.com.guigmarconi.mapper.ObjectMapper.parseObject;
import br.com.guigmarconi.model.Person;
import br.com.guigmarconi.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    PersonRepository personRepository;
    private Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());

    public PersonDTO findById(Long id){
        logger.info("Finding one Person!");

        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));

        return parseObject(entity, PersonDTO.class);
    }

    public List<PersonDTO> findAll(){
        logger.info("Finding them all!");

        return parseListObject(personRepository.findAll(), PersonDTO.class);
    }

    public PersonDTO createPerson(PersonDTO person){
        logger.info("Creating one Person!");

        var entity = parseObject(person, Person.class);

        return parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public PersonDTO updatePerson(PersonDTO person){
        logger.info("Updating one Person!");
        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public void delete(Long id){
        logger.info("Deleting one Person!");

        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));

        personRepository.delete(entity);
    }

}
