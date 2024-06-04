package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.model.MailingGroupModel;
import com.example.dosaaf_backend.model.MailingMemberModel;
import com.example.dosaaf_backend.service.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mailing")
public class MailingController {
    @Autowired
    private MailingService mailingService;

    @PostMapping("/group")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createGroup(@RequestBody MailingGroupModel mailingGroupModel){
        try{
            return ResponseEntity.ok(mailingService.createGroup(mailingGroupModel));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping("/member")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createMember(@RequestBody MailingMemberModel mailingMemberModel){
        try{
            return ResponseEntity.ok(mailingService.createMember(mailingMemberModel));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/group")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateGroup(@RequestBody MailingGroupModel mailingGroupModel){
        try{
            return ResponseEntity.ok(mailingService.updateGroup(mailingGroupModel));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/member")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateMember(@RequestBody MailingMemberModel mailingMemberModel){
        try{
            return ResponseEntity.ok(mailingService.updateMember(mailingMemberModel));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/group/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteGroup(@PathVariable Long id){
        try{
            return ResponseEntity.ok(mailingService.deleteGroup(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/member/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteMember(@PathVariable Long id){
        try{
            return ResponseEntity.ok(mailingService.deleteMember(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getAllGroupsWithMembers(){
        try{
            return ResponseEntity.ok(mailingService.getAll());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
