package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.entity.NewsEntity;
import com.example.dosaaf_backend.repository.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsRepo newsRepo;

    @PostMapping
    public ResponseEntity createNews(@RequestBody NewsEntity news){
        try{
            newsRepo.save(news);
            return ResponseEntity.ok("Новость была успешно создана");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }

    @GetMapping
    public ResponseEntity getNews(){
        try{
            return ResponseEntity.ok("Сервер работает");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }
}