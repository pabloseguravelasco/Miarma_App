package com.salesianostriana.dam.miarma.errors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 43876691117560211L;


    public PostNotFoundException(Long id) {
        super("No se puede encontrar el post con la ID: " + id);
    }
}