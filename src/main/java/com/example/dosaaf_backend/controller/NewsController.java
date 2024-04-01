package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.entity.NewsEntity;
import com.example.dosaaf_backend.exception.NewsNotFoundException;
import com.example.dosaaf_backend.repository.NewsRepo;
import com.example.dosaaf_backend.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping
    public ResponseEntity createNews(@RequestBody NewsEntity news){
        try{
            newsService.create(news);
            return ResponseEntity.ok("Новость была успешно создана");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity getOneNews(@RequestParam Long id){
        try{
            return ResponseEntity.ok(newsService.getOne(id));
        }
        catch (NewsNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }

    @GetMapping("/all")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getAllNews(){
        try{
            return ResponseEntity.ok(newsService.getAll());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNews(@PathVariable Long id){
        try{
            return ResponseEntity.ok(newsService.deleteNews(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }

    @PutMapping("/archive")
    public ResponseEntity inOrOutArchive(@RequestParam Long id){
        try{
            return ResponseEntity.ok(newsService.archive(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }
}