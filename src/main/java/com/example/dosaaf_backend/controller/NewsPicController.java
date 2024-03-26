package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.entity.NewsPicEntity;
import com.example.dosaaf_backend.service.NewsPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newspicture")
public class NewsPicController {

    @Autowired
    private NewsPicService newsPicService;

    @PutMapping("/makemain")
    public ResponseEntity makeMainPicture(@RequestParam Long id){
        try{
            return ResponseEntity.ok(newsPicService.makeMainPicture(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }

    @PostMapping
    public ResponseEntity createPicture(@RequestBody NewsPicEntity newsPicture, @RequestParam Long newsId){
        try{
            return ResponseEntity.ok(newsPicService.create(newsPicture, newsId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }
}
