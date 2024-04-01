package com.example.dosaaf_backend.exception;

public class UserEmailNotFoundException extends Exception{
    public UserEmailNotFoundException(String message) {
        super(message);
    }
}