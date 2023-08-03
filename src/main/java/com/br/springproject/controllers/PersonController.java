package com.br.springproject.controllers;

import com.br.springproject.dto.PersonDTO;
import com.br.springproject.entities.Person;
import com.br.springproject.repositories.PersonRepository;
import com.br.springproject.services.PersonServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;


import java.util.List;

@RestController
@RequestMapping("/person")
@Tag(name = "Persons", description = "Endpoints for managing Person")
public class PersonController {

    @Autowired
    private PersonServices personServices;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Finds All Persons", description = "Finds All Persons", tags = {"Persons"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))}),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {@Content})})
    public ResponseEntity<List<PersonDTO>> findAllPerson() {
        return ResponseEntity.ok().body(personServices.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Finds a Person", description = "Finds a Person", tags = {"Persons"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {@Content(schema = @Schema(implementation = PersonDTO.class))}),
                    @ApiResponse(description = "No Content", responseCode = "204", content = {@Content}),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {@Content})})
    public ResponseEntity<PersonDTO> findPersonById(@PathVariable Long id) {
        return ResponseEntity.ok().body(personServices.findById(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add a new Person", description = "Add a new Person", tags = {"Persons"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {@Content(schema = @Schema(implementation = PersonDTO.class))}),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {@Content})})
    public ResponseEntity<PersonDTO> addPerson(@RequestBody PersonDTO personDTO) {
        return ResponseEntity.ok().body(personServices.addPerson(personDTO));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates a Person", description = "Updates a User by passing in a JSON, representation of the Person.", tags = {"Persons"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200", content = {@Content(schema = @Schema(implementation = PersonDTO.class))}),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {@Content})})
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO personDTO, @PathVariable Long id) {
        return ResponseEntity.ok().body(personServices.updatePerson(personDTO, id));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a Person", description = "Deletes a Person by passing in a JSON, representation of the Person.", tags = {"Persons"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = {@Content}),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {@Content})})
    public ResponseEntity<Void> deletePersonById(@PathVariable Long id) {
        personServices.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
