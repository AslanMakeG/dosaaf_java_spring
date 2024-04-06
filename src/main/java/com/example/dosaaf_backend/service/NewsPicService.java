package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.NewsEntity;
import com.example.dosaaf_backend.entity.NewsPicEntity;
import com.example.dosaaf_backend.model.NewsPic;
import com.example.dosaaf_backend.repository.NewsPicRepo;
import com.example.dosaaf_backend.repository.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsPicService {
    @Autowired
    private NewsPicRepo newsPicRepo;
    @Autowired
    private NewsRepo newsRepo;

    public NewsPicEntity create(NewsPicEntity picture, Long newsId){
        NewsEntity news = newsRepo.findById(newsId).get();
        picture.setNews(news);
        return newsPicRepo.save(picture);
    }

    public NewsPic makeMainPicture(Long id){
        NewsPicEntity picture = newsPicRepo.findById(id).get();
        List<NewsPicEntity> pictures = newsPicRepo.findByNewsId(picture.getNews().getId());
        for(NewsPicEntity i: pictures){
            i.setMainPicture(i.getId() == id); //установка true/false в поле главной картинки
            newsPicRepo.save(i);
        }
        return NewsPic.toModel(picture);
    }
}
