package com.br.springproject.services;

import com.br.springproject.dto.PersonDTO;
import com.br.springproject.entities.Person;
import com.br.springproject.exceptions.ObjectNotFoundException;
import com.br.springproject.exceptions.RequiredObjectIsNulException;
import com.br.springproject.repositories.PersonRepository;
import com.google.gson.reflect.TypeToken;
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

import java.lang.reflect.Type;
import java.util.*;

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
    List<PersonDTO> listPersonDTO = new ArrayList<>();
    List<Person> listPersons = new ArrayList<>();

    @BeforeEach
    void setUpMocks() throws Exception {
        MockitoAnnotations.openMocks(this);
        startPerson();
    }

    @Test
    void findById() {
        Mockito.when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(person));

        Mockito.when(modelMapper.map(person, PersonDTO.class)).thenReturn(personDTO);

        PersonDTO response = personServices.findById(1L);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getName());
        assertNotNull(response.getBirthday());
        assertNotNull(response.getEmail());
        assertNotNull(response.getPassword());
        assertNotNull(response.getLinks());

        assertTrue(response.toString().contains("[<http://localhost/person/1>;rel=\"self\"]"));
        assertEquals(1L, response.getId());
        assertEquals("Joao", response.getName());
        assertEquals("joao@gmail.com", response.getEmail());
        assertEquals("123456789", response.getPassword());
    }

    @Test
    void findAll() {

        Type listType = new TypeToken<List<PersonDTO>>() {
        }.getType();

        Mockito.when(personRepository.findAll()).thenReturn(listPersons);

        Mockito.when(modelMapper.map(listPersons, listType)).thenReturn(listPersonDTO);

        List<PersonDTO> response = personServices.findAll();

        assertNotNull(response);
        assertNotNull(response.get(0).getId());
        assertNotNull(response.get(0).getName());
        assertNotNull(response.get(0).getEmail());
        assertNotNull(response.get(0).getBirthday());
        assertNotNull(response.get(0).getPassword());
        assertNotNull(response.get(0).getLinks());

        assertEquals(1L, response.get(0).getId());
        assertEquals("joao@gmail.com", response.get(0).getEmail());
        assertEquals("Joao", response.get(0).getName());
        assertEquals("123456789", response.get(0).getPassword());
        assertTrue(response.get(0).toString().contains("[<http://localhost/person/1>;rel=\"self\"]"));

        assertNotNull(response.get(1).getId());
        assertNotNull(response.get(1).getName());
        assertNotNull(response.get(1).getEmail());
        assertNotNull(response.get(1).getBirthday());
        assertNotNull(response.get(1).getPassword());
        assertNotNull(response.get(1).getLinks());

        assertEquals(2L, response.get(1).getId());
        assertEquals("maria@gmail.com", response.get(1).getEmail());
        assertEquals("Maria", response.get(1).getName());
        assertEquals("987654321", response.get(1).getPassword());
        assertTrue(response.get(1).toString().contains("[<http://localhost/person/2>;rel=\"self\"]"));

        assertFalse(response.isEmpty());
        assertEquals(12, response.size());

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
        assertNotNull(response.getLinks());

        assertEquals(1L, response.getId());
        assertEquals("Joao", response.getName());
        assertEquals("joao@gmail.com", response.getEmail());
        assertEquals("123456789", response.getPassword());


    }

    @Test
    void addPersonWithNullPerson() {

        Exception exception = assertThrows(RequiredObjectIsNulException.class, () -> {
           personServices.addPerson(null);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("It is not allowed to persist a null object!"));


    }

    @Test
    void updatePerson() {

        Mockito.when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(person));
        Mockito.when(modelMapper.map(personDTO, Person.class)).thenReturn(person);
        Mockito.when(personRepository.save(person)).thenReturn(person);
        Mockito.when(modelMapper.map(person, PersonDTO.class)).thenReturn(personDTO);

        PersonDTO response = personServices.updatePerson(personDTO);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getName());
        assertNotNull(response.getEmail());
        assertNotNull(response.getBirthday());
        assertNotNull(response.getPassword());
        assertNotNull(response.getLinks());

        assertEquals(1L, response.getId());
        assertEquals("Joao", response.getName());
        assertEquals("joao@gmail.com", response.getEmail());
        assertEquals("123456789", response.getPassword());

    }

    @Test
    void updatePersonWithNullPerson() {

        Exception exception = assertThrows(RequiredObjectIsNulException.class, () -> {
            personServices.updatePerson(null);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("It is not allowed to persist a null object!"));


    }

    @Test
    void deletePerson() {

        Mockito.when(personRepository.findById(Mockito.anyLong())).thenThrow(new ObjectNotFoundException("Person Not Found"));


        try {
            personServices.deletePerson(1L);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Person Not Found", ex.getMessage());

        }

    }

    @Test
    void deletePersonWithObjectNotFound(){
        Mockito.when(personRepository.findById(Mockito.anyLong())).thenThrow(new ObjectNotFoundException("Person Not Found"));

        try{
            personServices.deletePerson(1L);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Person Not Found", ex.getMessage());
        }
    }

    @Test
    void findPersonByIdReturnObjectNotFoundException() {

        Mockito.when(personRepository.findById(Mockito.anyLong())).thenThrow(new ObjectNotFoundException("Person Not Found"));

        try {
            personServices.findById(1L);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Person Not Found", ex.getMessage());
        }

    }


    private void startPerson() {
        person = new Person(1L, "Joao", "joao@gmail.com", "123456789", new Date(2003, Calendar.JANUARY, 4));
        personDTO = new PersonDTO(1L, "Joao", "joao@gmail.com", "123456789", new Date(2003, Calendar.JANUARY, 4));
        listPersons.add(new Person(1L, "Joao", "joao@gmail.com", "123456789", new Date(2003, Calendar.FEBRUARY, 4)));
        listPersons.add(new Person(2L, "Maria", "maria@gmail.com", "987654321", new Date(2000, Calendar.JANUARY, 15)));
        listPersonDTO.add(new PersonDTO(1L, "Joao", "joao@gmail.com", "123456789", new Date(2003, Calendar.FEBRUARY, 4)));
        listPersonDTO.add(new PersonDTO(2L, "Maria", "maria@gmail.com", "987654321", new Date(2000, Calendar.JANUARY, 15)));
    }

}