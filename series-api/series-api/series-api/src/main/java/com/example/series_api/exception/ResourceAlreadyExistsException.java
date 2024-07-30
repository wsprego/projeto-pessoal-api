package com.example.series_api.exception;

public class ResourceAlreadyExistsException extends RuntimeException{ //Para o tratamento de exeções personalizadas
    public ResourceAlreadyExistsException(String message){
        super(message);
    }

}
