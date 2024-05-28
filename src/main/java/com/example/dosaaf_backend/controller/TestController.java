package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.exception.test.QuestionTypeNotFound;
import com.example.dosaaf_backend.exception.test.TestNotFound;
import com.example.dosaaf_backend.model.TestModel;
import com.example.dosaaf_backend.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createTest(@RequestBody TestModel testModel){
        try{
            return ResponseEntity.ok(testService.create(testModel));
        }
        catch (QuestionTypeNotFound e){
            return ResponseEntity.badRequest().body("Произошла ошибка " + e);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка " + e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteTest(@PathVariable Long id){
        try{
            return ResponseEntity.ok(testService.delete(id));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка " + e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneTest(@PathVariable Long id){
        try{
            return ResponseEntity.ok(testService.getOne(id));
        }
        catch (TestNotFound e){
            return ResponseEntity.badRequest().body("Произошла ошибка " + e);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка " + e);
        }
    }

    @GetMapping
    public ResponseEntity getAllTests(){
        try{
            return ResponseEntity.ok(testService.getAll());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка " + e);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateTest(@RequestBody TestModel testModel){
        try{
            return ResponseEntity.ok(testService.update(testModel));
        }
        catch (QuestionTypeNotFound e){
            return ResponseEntity.badRequest().body("Произошла ошибка " + e);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка " + e);
        }
    }
}
