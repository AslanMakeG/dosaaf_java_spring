package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.entity.PartnerEntity;
import com.example.dosaaf_backend.exception.partner.PartnerNotFoundException;
import com.example.dosaaf_backend.model.PartnerCreationModel;
import com.example.dosaaf_backend.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partner")
public class PartnerController {
    @Autowired
    private PartnerService partnerService;

    @GetMapping("/{id}")
    public ResponseEntity getOnePartner(@PathVariable Long id){
        try{
            return ResponseEntity.ok(partnerService.getOne(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
    @GetMapping("/all")
    public ResponseEntity getAllPartners(){
        try{
            return ResponseEntity.ok(partnerService.getAll());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createPartner(@ModelAttribute PartnerCreationModel partnerCreationModel){
        try{
            return ResponseEntity.ok(partnerService.create(partnerCreationModel));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updatePartner(@ModelAttribute PartnerCreationModel partnerCreationModel){
        try{
            return ResponseEntity.ok(partnerService.update(partnerCreationModel));
        }
        catch (PartnerNotFoundException e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка " + e);
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deletePartner(@PathVariable Long id){
        try{
            return ResponseEntity.ok(partnerService.delete(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
