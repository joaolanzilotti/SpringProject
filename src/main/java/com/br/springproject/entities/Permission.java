package com.br.springproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Entity
@Table(name = "permission")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements GrantedAuthority,Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Override
    public String getAuthority() {
        return this.description;
    }
}
