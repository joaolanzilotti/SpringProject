package com.br.springproject.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "email", "password", "birthday"})
public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {

    private Long id;
    private String name;
    //@JsonProperty("e-mail") -> Troca o nome do campo
    private String email;
    //@JsonIgnore -> Ignora este campo
    private String password;
    @Temporal(TemporalType.DATE)
    private Date birthday;

}
