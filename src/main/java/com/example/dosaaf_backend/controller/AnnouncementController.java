package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.model.AnnouncementModel;
import com.example.dosaaf_backend.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createAnnouncement(@ModelAttribute AnnouncementModel announcementModel){
        try{
            return ResponseEntity.ok(announcementService.create(announcementModel));
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().body("Произошла ошибка" + e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllAnnouncements(){
        try{
            return ResponseEntity.ok(announcementService.getAll());
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteAnnouncement(@PathVariable Long id){
        try{
            return ResponseEntity.ok(announcementService.delete(id));
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().body("Произошла ошибка " + e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneAnnouncement(@PathVariable Long id){
        try{
            return ResponseEntity.ok(announcementService.getOne(id));
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().body("Произошла ошибка " + e);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateAnnouncement(@ModelAttribute AnnouncementModel announcementModel){
        try{
            return ResponseEntity.ok(announcementService.update(announcementModel));
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().body("Произошла ошибка " + e);
        }
    }
}
