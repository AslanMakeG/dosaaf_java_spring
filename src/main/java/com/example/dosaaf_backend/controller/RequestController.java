package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.entity.RequestEntity;
import com.example.dosaaf_backend.exception.request.RequestStatusNotFoundException;
import com.example.dosaaf_backend.exception.service.ServiceNotFoundException;
import com.example.dosaaf_backend.exception.user.UserNotFoundException;
import com.example.dosaaf_backend.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    //userId ставить -1, если это неавторизованный пользователь
    //Если авторизованный то поля userEmail, userName, userSurname и userPatronymic можно не заполнять
    @PostMapping
    public ResponseEntity createRequest(@RequestBody RequestEntity requestEntity, @RequestParam Long userId,
                                        @RequestParam Long serviceId){
        try{
            return ResponseEntity.ok(requestService.create(requestEntity, userId, serviceId));
        }
        catch (RequestStatusNotFoundException | UserNotFoundException | ServiceNotFoundException e){
            return ResponseEntity.badRequest().body("Произошла ошибка " + e);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
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
    public ResponseEntity rejectRequest(@PathVariable Long id){
        try{
            return ResponseEntity.ok(requestService.reject(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/accept/{id}")
    public ResponseEntity acceptRequest(@PathVariable Long id){
        try{
            return ResponseEntity.ok(requestService.accept(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}

