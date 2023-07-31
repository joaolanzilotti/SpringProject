package com.br.springproject.controllers;

import com.br.springproject.dto.PersonDTO;
import com.br.springproject.entities.Person;
import com.br.springproject.repositories.PersonRepository;
import com.br.springproject.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;


import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices personServices;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonDTO>> findAllPerson() {
        return ResponseEntity.ok().body(personServices.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> findPersonById(@PathVariable Long id) {
        return ResponseEntity.ok().body(personServices.findById(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> addPerson(@RequestBody PersonDTO personDTO) {
        return ResponseEntity.ok().body(personServices.addPerson(personDTO));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO personDTO, @PathVariable Long id) {
        return ResponseEntity.ok().body(personServices.updatePerson(personDTO, id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePersonById(@PathVariable Long id) {
        personServices.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
