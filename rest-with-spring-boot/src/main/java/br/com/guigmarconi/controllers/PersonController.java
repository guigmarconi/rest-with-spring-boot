package br.com.guigmarconi.controllers;

import br.com.guigmarconi.data.dto.v1.PersonDTO;
import br.com.guigmarconi.data.dto.v2.PersonDTOV2;
import br.com.guigmarconi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonDTO> findAll(){

        return service.findAll();
    }

    @GetMapping(    value = "/{id}",
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO findById(@PathVariable("id") Long id){

        return service.findById(id);
    }

    @PostMapping(   produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO create(@RequestBody PersonDTO person){

        return service.createPerson(person);
    }

    @PostMapping(   value = "/v2",
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTOV2 create(@RequestBody PersonDTOV2 person){

        return service.createPersonV2(person);
    }

    @PutMapping(    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO update(@RequestBody PersonDTO person){

        return service.updatePerson(person);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
         service.delete(id);
         return ResponseEntity.noContent().build();
    }
}
