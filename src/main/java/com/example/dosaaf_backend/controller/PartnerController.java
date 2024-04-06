package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.entity.PartnerEntity;
import com.example.dosaaf_backend.exception.PartnerNotFoundException;
import com.example.dosaaf_backend.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity createPartner(@RequestBody PartnerEntity partnerEntity){
        try{
            return ResponseEntity.ok(partnerService.create(partnerEntity));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping
    public ResponseEntity updatePartner(@RequestBody PartnerEntity partnerEntity){
        try{
            return ResponseEntity.ok(partnerService.update(partnerEntity));
        }
        catch (PartnerNotFoundException e){
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deletePartner(@PathVariable Long id){
        try{
            return ResponseEntity.ok(partnerService.delete(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
