package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.entity.ServiceEntity;
import com.example.dosaaf_backend.entity.ServiceSectionEntity;
import com.example.dosaaf_backend.exception.service.ServiceAlreadyExistsException;
import com.example.dosaaf_backend.exception.service.ServiceNotFoundException;
import com.example.dosaaf_backend.exception.service.ServiceSectionNotFoundException;
import com.example.dosaaf_backend.model.ServiceModel;
import com.example.dosaaf_backend.model.ServiceSectionModel;
import com.example.dosaaf_backend.service.ServiceSectionService;
import com.example.dosaaf_backend.service.ServiceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private ServiceSectionService serviceSectionService;

    @GetMapping("/all")
    public ResponseEntity getAll(HttpServletRequest request){
        try{
            return ResponseEntity.ok(serviceSectionService.getSectionsAndServices());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id){
        try{
            return ResponseEntity.ok(serviceService.getOne(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка" + e);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createService(@RequestBody ServiceEntity serviceSectionEntity,
                                               @RequestParam Long serviceSectionId){
        try{
            return ResponseEntity.ok(serviceService.create(serviceSectionEntity, serviceSectionId));
        }
        catch (ServiceAlreadyExistsException | ServiceSectionNotFoundException e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateService(@RequestBody ServiceModel serviceModel){
        try{
            return ResponseEntity.ok(serviceService.update(serviceModel));
        }
        catch (ServiceNotFoundException | ServiceSectionNotFoundException e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteService(@PathVariable Long id){
        try{
            return ResponseEntity.ok(serviceService.delete(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/section/{id}")
    public ResponseEntity createServiceSection(@PathVariable Long id){
        try{
            return ResponseEntity.ok(serviceSectionService.getOne(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping("/section")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createServiceSection(@RequestBody ServiceSectionEntity serviceSectionEntity){
        try{
            return ResponseEntity.ok(serviceSectionService.create(serviceSectionEntity));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/section")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateServiceSection(@RequestBody ServiceSectionModel serviceSectionModel){
        try{
            return ResponseEntity.ok(serviceSectionService.update(serviceSectionModel));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/section/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteServiceSection(@PathVariable Long id){
        try{
            return ResponseEntity.ok(serviceSectionService.delete(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
