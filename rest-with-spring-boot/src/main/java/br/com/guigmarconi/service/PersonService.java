package br.com.guigmarconi.service;

import br.com.guigmarconi.controllers.PersonController;
import br.com.guigmarconi.controllers.TestLogController;
import br.com.guigmarconi.data.dto.v1.PersonDTO;
import br.com.guigmarconi.data.dto.v2.PersonDTOV2;
import br.com.guigmarconi.exception.RequiredObjectIsNullException;
import br.com.guigmarconi.exception.ResourceNotFoundException;
import br.com.guigmarconi.mapper.custom.PersonMapper;
import br.com.guigmarconi.model.Person;
import br.com.guigmarconi.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.guigmarconi.mapper.ObjectMapper.parseListObject;
import static br.com.guigmarconi.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonMapper converter;
    private Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());

    public PersonDTO findById(Long id){
        logger.info("Finding one Person!");

        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));

        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public List<PersonDTO> findAll(){
        logger.info("Finding them all!");

        var persons = parseListObject(personRepository.findAll(), PersonDTO.class);
        persons.forEach(this::addHateoasLinks);
        return persons;
    }

    public PersonDTO createPerson(PersonDTO person){

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Person!");

        var entity = parseObject(person, Person.class);

        var dto = parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTOV2 createPersonV2(PersonDTOV2 person){

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Person V2!");

        var entity = converter.convertDTOtoEntity(person);

        return converter.convertEntityToDTO(personRepository.save(entity));
    }

    public PersonDTO updatePerson(PersonDTO person){

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Person!");
        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting one Person!");

        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));

        personRepository.delete(entity);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));

        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));

        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));

        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));

        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }

}
