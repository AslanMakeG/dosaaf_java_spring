package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.exception.user.UserAlreadyExsistsException;
import com.example.dosaaf_backend.exception.user.UserEmailNotFoundException;
import com.example.dosaaf_backend.exception.user.UserNotActivatedException;
import com.example.dosaaf_backend.exception.user.UserNotFoundException;
import com.example.dosaaf_backend.model.ResetPasswordRequest;
import com.example.dosaaf_backend.security.Pojo.LoginRequest;
import com.example.dosaaf_backend.security.Pojo.SingupRequest;
import com.example.dosaaf_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
        catch(BadCredentialsException e){
            return ResponseEntity.badRequest().body("неверный пароль");
        }
        catch(UserEmailNotFoundException | UserNotActivatedException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка: " + e);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SingupRequest singupRequest){
        try{
            return ResponseEntity.ok(userService.create(singupRequest));
        }
        catch (UserAlreadyExsistsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка: " + e);
        }
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity activateEmail(@PathVariable String code){
        try{
            return ResponseEntity.ok(userService.activate(code));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }

    @GetMapping("/password/forgot/{email}")
    public ResponseEntity forgotPassword(@PathVariable String email){
        try{
            return ResponseEntity.ok(userService.forgotPassword(email));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }

    @PostMapping("/password/reset")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        try{
            return ResponseEntity.ok(userService.resetPassword(resetPasswordRequest));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getUserInfo(Principal principal){
        try{
            return ResponseEntity.ok(userService.getUserInfoByEmail(principal.getName()));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }
}
