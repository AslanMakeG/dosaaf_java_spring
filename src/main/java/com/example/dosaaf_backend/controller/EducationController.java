package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.exception.education.EducationNotFoundException;
import com.example.dosaaf_backend.model.EducationModel;
import com.example.dosaaf_backend.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/education")
public class EducationController {
    @Autowired
    private EducationService educationService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity getOneEducation(@PathVariable Long id){
        try{
            return ResponseEntity.ok(educationService.getOne(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity getAllEducations(){
        try{
            return ResponseEntity.ok(educationService.getAll());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createEducation(@ModelAttribute EducationModel educationModel){
        try{
            return ResponseEntity.ok(educationService.create(educationModel));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateEducation(@ModelAttribute EducationModel educationModel){
        try{
            return ResponseEntity.ok(educationService.update(educationModel));
        }
        catch (EducationNotFoundException e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка " + e);
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteEducation(@PathVariable Long id){
        try{
            return ResponseEntity.ok(educationService.delete(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
