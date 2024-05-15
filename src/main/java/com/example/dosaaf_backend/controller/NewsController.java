package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.entity.NewsEntity;
import com.example.dosaaf_backend.exception.news.NewsNotFoundException;
import com.example.dosaaf_backend.model.NewsModel;
import com.example.dosaaf_backend.service.NewsService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity createNews(@RequestBody NewsModel news){
        try{
            return ResponseEntity.ok(newsService.create(news));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка" + e);
        }
    }

    //Получить одну новость для просмотра
    @GetMapping("/{id}")
    public ResponseEntity getOneNews(@PathVariable Long id){
        try{
            return ResponseEntity.ok(newsService.getOne(id));
        }
        catch (NewsNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка: " + e);
        }
    }

    //Получить все новости для страницы новостей
    @GetMapping("/all")
    public ResponseEntity getAllNews(){
        try{
            return ResponseEntity.ok(newsService.getAll());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }

    @GetMapping
    public ResponseEntity getNewsByPage(@RequestParam Integer page, @RequestParam Integer limit,
                                        @RequestParam @Nullable List<String> query){
        try{
            return ResponseEntity.ok(newsService.getByPage(page, limit, query));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }

    @GetMapping("/count")
    public ResponseEntity getNewsCount(@RequestParam @Nullable List<String> query){
        try{
            return ResponseEntity.ok(newsService.getCount(query));
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