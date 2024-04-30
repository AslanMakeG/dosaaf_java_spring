package com.example.dosaaf_backend.controller;

import com.example.dosaaf_backend.entity.AnnouncementEntity;
import com.example.dosaaf_backend.model.AnnouncementCreationModel;
import com.example.dosaaf_backend.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity createAnnouncement(@ModelAttribute AnnouncementCreationModel announcementCreationModel){
        try{
            return ResponseEntity.ok(announcementService.create(announcementCreationModel));
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }
}
