package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.exception.test.QuestionTypeNotFound;
import com.example.dosaaf_backend.model.TestModel;
import com.example.dosaaf_backend.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;

    @PostMapping
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
}
