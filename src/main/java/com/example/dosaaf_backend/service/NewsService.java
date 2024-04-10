package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.NewsEntity;
import com.example.dosaaf_backend.entity.NewsPicEntity;
import com.example.dosaaf_backend.exception.NewsNotFoundException;
import com.example.dosaaf_backend.model.News;
import com.example.dosaaf_backend.model.NewsPic;
import com.example.dosaaf_backend.repository.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private NewsPicService newsPicService;

    public News create(NewsEntity news){
        NewsEntity newsCreated = newsRepo.save(news);

        //условно создаем по 3 картинки для новости
        for(int i = 0; i < 3; i++){
            NewsPicEntity picture = new NewsPicEntity();
            picture.setPictureLink("Ссылка на картинку " + (i+1));
            picture.setMainPicture(i == 0);
            newsPicService.create(picture, newsCreated.getId());
        }
        return News.toModel(newsCreated);
    }

    public News getOne(Long id) throws NewsNotFoundException {
        NewsEntity news = newsRepo.findById(id).orElse(null);

        if(news == null){
            throw new NewsNotFoundException("Новость с таким Id не была найдена");
        }

        return News.toModel(news);
    }


    public List<News> getAll() {
        List<News> newsList = new ArrayList<>();
        newsRepo.findAll().forEach(news ->{
            News model = new News();
            model.setId(news.getId());
            model.setTitle(news.getTitle());
            model.setContent(news.getContent());
            model.setCreationDateTime(news.getCreationDateTime());
            model.setInArchive(news.isInArchive());

            List<NewsPic> newsPics = new ArrayList<>();
            newsPics.add(NewsPic.toModel(news.getMainPicture()));

            model.setPictures(newsPics);

            newsList.add(model);
        });

        return newsList;
    }

    public Long deleteNews(Long id){
        newsRepo.deleteById(id);
        return id;
    }

    public News archive(Long id){
        NewsEntity news = newsRepo.findById(id).get();
        news.setInArchive(!news.isInArchive());
        newsRepo.save(news);
        return News.toModel(news);
    }

    public News update(NewsEntity newsEntity){
        NewsEntity news = newsRepo.findById(newsEntity.getId()).get();
        //Отправлено null, чтобы не менять фотографии новости
        //Иначе поменять фотографии на другие
        if(newsEntity.getPictures() != null){
            news.setPictures(newsEntity.getPictures());
        }
        news.setContent(newsEntity.getContent());
        news.setTitle(newsEntity.getTitle());
        //!!! Тут сделать обновление ссылки на альбом и обновление всех фотографий с нее !!!
        news.setAlbumLink(newsEntity.getAlbumLink());
        newsRepo.save(news);
        return News.toModel(news);
    }
}
