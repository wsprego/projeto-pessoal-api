package com.example.series_api.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){ //Para o tratamento de exeções personalizadas
        super(message);
    }
}
