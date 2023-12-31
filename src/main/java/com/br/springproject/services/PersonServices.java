package com.br.springproject.services;

import com.br.springproject.dto.PersonDTO;
import com.br.springproject.entities.Person;
import com.br.springproject.exceptions.ObjectNotFoundException;
import com.br.springproject.repositories.PersonRepository;
import com.google.gson.reflect.TypeToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PersonDTO findById(Long id){
        logger.info("Finding person by id");
        return modelMapper.map(personRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Person Not Found!")), PersonDTO.class);
    }

    public List<PersonDTO> findAll(){
        logger.info("Finding all persons!");
        List<Person> listPersons = personRepository.findAll();
        Type listType = new TypeToken<List<PersonDTO>>() {}.getType();

        return modelMapper.map(listPersons, listType);
    }

    public PersonDTO addPerson(PersonDTO personDTO){
        logger.info("Adding new person");
        Person person = personRepository.save(modelMapper.map(personDTO, Person.class));
        return modelMapper.map(person, PersonDTO.class);
    }

    public PersonDTO updatePerson(PersonDTO personDTO, Long id){
        personRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No records found for this ID!"));
        logger.info("Updating person");
        personDTO.setId(id);
        Person person = personRepository.save(modelMapper.map(personDTO, Person.class));
        return modelMapper.map(person, PersonDTO.class);
    }

    public void deletePerson(Long id){
        logger.info("Deleting person");
        personRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No records found for this ID!"));
        personRepository.deleteById(id);
    }

}
