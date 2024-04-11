package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.exception.user.UserAlreadyExsistsException;
import com.example.dosaaf_backend.security.Pojo.LoginRequest;
import com.example.dosaaf_backend.security.Pojo.SingupRequest;
import com.example.dosaaf_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody LoginRequest loginRequest){
        try {
            return ResponseEntity.ok(userService.auth(loginRequest));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SingupRequest singupRequest){
        try{
            return ResponseEntity.ok(userService.create(singupRequest));
        }
        catch (UserAlreadyExsistsException e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }
}
