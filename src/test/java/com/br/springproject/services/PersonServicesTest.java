package com.br.springproject.services;

import com.br.springproject.dto.PersonDTO;
import com.br.springproject.entities.Person;
import com.br.springproject.exceptions.ObjectNotFoundException;
import com.br.springproject.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    @InjectMocks
    private PersonServices personServices;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ModelMapper modelMapper;


    private Person person;
    private PersonDTO personDTO;

    @BeforeEach
    void setUpMocks() throws Exception{
        MockitoAnnotations.openMocks(this);
        startPerson();
    }

    @Test
    void findById() {
        // Create and set up the person mock
        Mockito.when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(person));

        // Set up the ModelMapper mock to return the expected PersonDTO
        Mockito.when(modelMapper.map(person, PersonDTO.class)).thenReturn(personDTO);

        // Test the findById method
        PersonDTO response = personServices.findById(1L);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getName());
        assertNotNull(response.getBirthday());
        assertNotNull(response.getEmail());
        assertNotNull(response.getPassword());

        System.out.println(response.toString());
        assertTrue(response.toString().contains("[<http://localhost/person/1>;rel=\"self\"]"));
        assertEquals(1L, response.getId());
        assertEquals("Joao", response.getName());
        assertEquals("joao@gmail.com", response.getEmail());
        assertEquals("123456789", response.getPassword());
    }

    @Test
    void findAll() {
    }

    @Test
    void addPerson() {

        Mockito.when(modelMapper.map(personDTO, Person.class)).thenReturn(person);
        Mockito.when(modelMapper.map(person, PersonDTO.class)).thenReturn(personDTO);
        Mockito.when(personRepository.save(Mockito.any())).thenReturn(person);

        PersonDTO response = personServices.addPerson(personDTO);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getName());
        assertNotNull(response.getBirthday());
        assertNotNull(response.getEmail());
        assertNotNull(response.getPassword());

        System.out.println(response.toString());
        assertEquals(1L, response.getId());
        assertEquals("Joao", response.getName());
        assertEquals("joao@gmail.com", response.getEmail());
        assertEquals("123456789", response.getPassword());


    }

    @Test
    void updatePerson() {

        Mockito.when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(person));
        Mockito.when(modelMapper.map(personDTO, Person.class)).thenReturn(person);
        Mockito.when(personRepository.save(person)).thenReturn(person);
        Mockito.when(modelMapper.map(person, PersonDTO.class)).thenReturn(personDTO);

        PersonDTO response = personServices.updatePerson(personDTO, 1L);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getName());
        assertNotNull(response.getEmail());
        assertNotNull(response.getBirthday());
        assertNotNull(response.getPassword());

        assertEquals(1L, response.getId());
        assertEquals("Joao", response.getName());
        assertEquals("joao@gmail.com", response.getEmail());
        assertEquals("123456789", response.getPassword());

    }

    @Test
    void deletePerson() {

        Mockito.when(personRepository.findById(Mockito.anyLong())).thenThrow(new ObjectNotFoundException("Person Not Found"));


        try {
            personServices.deletePerson(1L);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Person Not Found", ex.getMessage());

        }

    }

    private void startPerson(){
        person = new Person(1L, "Joao", "joao@gmail.com", "123456789", new Date(2003, Calendar.JANUARY, 4));
        personDTO = new PersonDTO(1L, "Joao", "joao@gmail.com", "123456789", new Date(2003, Calendar.JANUARY, 4));
    }

}