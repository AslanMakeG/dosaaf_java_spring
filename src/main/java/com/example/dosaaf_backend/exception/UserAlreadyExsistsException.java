package com.example.dosaaf_backend.exception;

public class UserAlreadyExsistsException extends Exception{
    public UserAlreadyExsistsException(String message){
        super(message);
    }
}
