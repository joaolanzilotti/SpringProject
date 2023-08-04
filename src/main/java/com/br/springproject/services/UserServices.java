package com.br.springproject.services;

import com.br.springproject.controllers.UserController;
import com.br.springproject.dto.UserDTO;
import com.br.springproject.entities.User;
import com.br.springproject.exceptions.ObjectNotFoundException;
import com.br.springproject.exceptions.RequiredObjectIsNulException;
import com.br.springproject.repositories.UserRepository;
import com.google.gson.reflect.TypeToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

@Service
public class UserServices implements UserDetailsService {

    private Logger logger = Logger.getLogger(UserServices.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO findById(Long id) {
        logger.info("Finding person by id");
        UserDTO userDTO = modelMapper.map(userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User Not Found!")), UserDTO.class);
        userDTO.add(linkTo(methodOn(UserController.class).findPersonById(id)).withSelfRel());
        return userDTO;
    }

    public List<UserDTO> findAll() {
        logger.info("Finding all users!");
        List<User> listUsers = userRepository.findAll();
        Type listType = new TypeToken<List<UserDTO>>() {
        }.getType();

        List<UserDTO> listUserDTO = modelMapper.map(listUsers, listType);
        listUserDTO.stream().forEach(dto -> dto.add(linkTo(methodOn(UserController.class).findPersonById(dto.getId())).withSelfRel()));
        return listUserDTO;
    }

    public UserDTO addPerson(UserDTO userDTO) {

        if (userDTO == null) throw new RequiredObjectIsNulException();

        logger.info("Adding new user");
        User user = userRepository.save(modelMapper.map(userDTO, User.class));
        UserDTO pd = modelMapper.map(user, UserDTO.class);
        pd.add(linkTo(methodOn(UserController.class).findPersonById(pd.getId())).withSelfRel());
        return pd;
    }

    public UserDTO updatePerson(UserDTO userDTO) {
        if (userDTO == null) throw new RequiredObjectIsNulException();
        userRepository.findById(userDTO.getId()).orElseThrow(() -> new ObjectNotFoundException("No records found for this ID!"));
        logger.info("Updating user");
        userDTO.setId(userDTO.getId());
        User user = userRepository.save(modelMapper.map(userDTO, User.class));
        UserDTO pd = modelMapper.map(user, UserDTO.class);
        pd.add(linkTo(methodOn(UserController.class).updatePerson(userDTO)).withSelfRel());
        return pd;
    }

    public void deletePerson(Long id) {
        logger.info("Deleting user");
        userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No records found for this ID!"));
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one User By name " + username + "!");
        User user = userRepository.findByEmail(username);
        if(user != null){
            return user;
        }else{
            throw new UsernameNotFoundException("Username " + username + " Not found!");
        }
    }
}
