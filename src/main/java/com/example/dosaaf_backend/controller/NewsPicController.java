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

    @PutMapping("/makemain/{id}")
    public ResponseEntity makeMainPicture(@PathVariable Long id){
        try{
            return ResponseEntity.ok(newsPicService.makeMainPicture(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }

    @GetMapping
    public ResponseEntity getFromAlbumLink(@RequestParam String albumLink){
        try{
            return ResponseEntity.ok(newsPicService.getFromAlbumLink(albumLink));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e);
        }
    }
}
