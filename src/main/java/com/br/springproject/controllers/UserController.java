package com.br.springproject.controllers;

import com.br.springproject.dto.UserDTO;
import com.br.springproject.services.UserServices;
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


import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "Endpoints for managing User")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Finds All Persons", description = "Finds All Persons", tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))}),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {@Content})})
    public ResponseEntity<List<UserDTO>> findAllPerson() {
        return ResponseEntity.ok().body(userServices.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Finds a User", description = "Finds a User", tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {@Content(schema = @Schema(implementation = UserDTO.class))}),
                    @ApiResponse(description = "No Content", responseCode = "204", content = {@Content}),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {@Content})})
    public ResponseEntity<UserDTO> findPersonById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userServices.findById(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add a new User", description = "Add a new User", tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {@Content(schema = @Schema(implementation = UserDTO.class))}),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {@Content})})
    public ResponseEntity<UserDTO> addPerson(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok().body(userServices.addPerson(userDTO));
    }

    @PutMapping( produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates a User", description = "Updates a User by passing in a JSON, representation of the User.", tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200", content = {@Content(schema = @Schema(implementation = UserDTO.class))}),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {@Content})})
    public ResponseEntity<UserDTO> updatePerson(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok().body(userServices.updatePerson(userDTO));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a User", description = "Deletes a User by passing in a JSON, representation of the User.", tags = {"Users"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = {@Content}),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {@Content})})
    public ResponseEntity<Void> deletePersonById(@PathVariable Long id) {
        userServices.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
