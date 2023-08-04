package com.br.springproject.services;

import com.br.springproject.dto.UserDTO;
import com.br.springproject.entities.User;
import com.br.springproject.exceptions.ObjectNotFoundException;
import com.br.springproject.exceptions.RequiredObjectIsNulException;
import com.br.springproject.repositories.UserRepository;
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
class UserServicesTest {

    @InjectMocks
    private UserServices userServices;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;


    private User user;
    private UserDTO userDTO;
    List<UserDTO> listUserDTO = new ArrayList<>();
    List<User> listUsers = new ArrayList<>();

    @BeforeEach
    void setUpMocks() throws Exception {
        MockitoAnnotations.openMocks(this);
        startPerson();
    }

    @Test
    void findById() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        Mockito.when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO response = userServices.findById(1L);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getName());
        assertNotNull(response.getBirthday());
        assertNotNull(response.getEmail());
        assertNotNull(response.getPassword());
        assertNotNull(response.getLinks());

        assertTrue(response.toString().contains("[<http://localhost/user/1>;rel=\"self\"]"));
        assertEquals(1L, response.getId());
        assertEquals("Joao", response.getName());
        assertEquals("joao@gmail.com", response.getEmail());
        assertEquals("123456789", response.getPassword());
    }

    @Test
    void findAll() {

        Type listType = new TypeToken<List<UserDTO>>() {
        }.getType();

        Mockito.when(userRepository.findAll()).thenReturn(listUsers);

        Mockito.when(modelMapper.map(listUsers, listType)).thenReturn(listUserDTO);

        List<UserDTO> response = userServices.findAll();

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
        assertTrue(response.get(0).toString().contains("[<http://localhost/user/1>;rel=\"self\"]"));

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
        assertTrue(response.get(1).toString().contains("[<http://localhost/user/2>;rel=\"self\"]"));

        assertFalse(response.isEmpty());
        assertEquals(12, response.size());

    }

    @Test
    void addPerson() {

        Mockito.when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        Mockito.when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDTO response = userServices.addPerson(userDTO);

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
           userServices.addPerson(null);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("It is not allowed to persist a null object!"));


    }

    @Test
    void updatePerson() {

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO response = userServices.updatePerson(userDTO);

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
            userServices.updatePerson(null);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("It is not allowed to persist a null object!"));


    }

    @Test
    void deletePerson() {

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenThrow(new ObjectNotFoundException("User Not Found"));


        try {
            userServices.deletePerson(1L);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("User Not Found", ex.getMessage());

        }

    }

    @Test
    void deletePersonWithObjectNotFound(){
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenThrow(new ObjectNotFoundException("User Not Found"));

        try{
            userServices.deletePerson(1L);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("User Not Found", ex.getMessage());
        }
    }

    @Test
    void findPersonByIdReturnObjectNotFoundException() {

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenThrow(new ObjectNotFoundException("User Not Found"));

        try {
            userServices.findById(1L);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("User Not Found", ex.getMessage());
        }

    }


    private void startPerson() {
        user = new User(1L, "Joao", "joao@gmail.com", "123456789", new Date(2003, Calendar.JANUARY, 4));
        userDTO = new UserDTO(1L, "Joao", "joao@gmail.com", "123456789", new Date(2003, Calendar.JANUARY, 4));
        listUsers.add(new User(1L, "Joao", "joao@gmail.com", "123456789", new Date(2003, Calendar.FEBRUARY, 4)));
        listUsers.add(new User(2L, "Maria", "maria@gmail.com", "987654321", new Date(2000, Calendar.JANUARY, 15)));
        listUserDTO.add(new UserDTO(1L, "Joao", "joao@gmail.com", "123456789", new Date(2003, Calendar.FEBRUARY, 4)));
        listUserDTO.add(new UserDTO(2L, "Maria", "maria@gmail.com", "987654321", new Date(2000, Calendar.JANUARY, 15)));
    }

}