package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.entity.ServiceEntity;
import com.example.dosaaf_backend.entity.ServiceSectionEntity;
import com.example.dosaaf_backend.exception.service.ServiceAlreadyExistsException;
import com.example.dosaaf_backend.exception.service.ServiceSectionNotFoundException;
import com.example.dosaaf_backend.service.ServiceSectionService;
import com.example.dosaaf_backend.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private ServiceSectionService serviceSectionService;

    @GetMapping("/all")
    public ResponseEntity getAll(){
        try{
            return ResponseEntity.ok(serviceSectionService.getSectionsAndServices());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping("/create")
    public ResponseEntity createService(@RequestBody ServiceEntity serviceSectionEntity,
                                               @RequestParam Long serviceSectionID){
        try{
            return ResponseEntity.ok(serviceService.create(serviceSectionEntity, serviceSectionID));
        }
        catch (ServiceAlreadyExistsException | ServiceSectionNotFoundException e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteService(@PathVariable Long id){
        try{
            return ResponseEntity.ok(serviceService.delete(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping("/section/create")
    public ResponseEntity createServiceSection(@RequestBody ServiceSectionEntity serviceSectionEntity){
        try{
            return ResponseEntity.ok(serviceSectionService.create(serviceSectionEntity));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/section/delete/{id}")
    public ResponseEntity deleteServiceSection(@PathVariable Long id){
        try{
            return ResponseEntity.ok(serviceSectionService.delete(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
