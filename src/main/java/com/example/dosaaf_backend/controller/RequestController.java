package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.exception.request.RequestStatusNotFoundException;
import com.example.dosaaf_backend.exception.service.ServiceNotFoundException;
import com.example.dosaaf_backend.exception.user.UserNotFoundException;
import com.example.dosaaf_backend.model.RequestCreationModel;
import com.example.dosaaf_backend.service.RequestService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    //userId ставить -1, если это неавторизованный пользователь
    //Если авторизованный то поля userEmail, userName, userSurname и userPatronymic можно не заполнять
    @PostMapping
    public ResponseEntity createRequest(@RequestBody RequestCreationModel requestModel, @Nullable Principal principal,
                                        @RequestParam Long serviceId){
        try{
            return ResponseEntity.ok(requestService.create(requestModel, principal == null ? null : principal.getName(), serviceId));
        }
        catch (RequestStatusNotFoundException | UserNotFoundException | ServiceNotFoundException e){
            return ResponseEntity.badRequest().body("Произошла ошибка " + e);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getAllRequestsFromUser(Principal principal){
        try{
            return ResponseEntity.ok(requestService.getAllFromUser(principal.getName()));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка " + e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllRequests(){
        try{
            return ResponseEntity.ok(requestService.getAll());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка " + e);
        }
    }

    @PutMapping("/reject/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity rejectRequest(@PathVariable Long id){
        try{
            return ResponseEntity.ok(requestService.reject(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/accept/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity acceptRequest(@PathVariable Long id){
        try{
            return ResponseEntity.ok(requestService.accept(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}

