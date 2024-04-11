package com.example.dosaaf_backend.exception.service;

public class ServiceAlreadyExistsException extends Exception{
    public ServiceAlreadyExistsException(String message) {
        super(message);
    }
}
