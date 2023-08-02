package com.br.springproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNulException extends RuntimeException{

    public RequiredObjectIsNulException(){
        super("It is not allowed to persist a null object!");
    }

    public RequiredObjectIsNulException(String ex){
        super(ex);
    }

}
