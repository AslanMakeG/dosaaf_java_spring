package com.example.dosaaf_backend.exception.user;

public class OldPasswordDontMatchException extends Exception{
    public OldPasswordDontMatchException(String message) {
        super(message);
    }
}
