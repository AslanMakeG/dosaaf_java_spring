package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.NewsEntity;
import com.example.dosaaf_backend.entity.NewsPicEntity;
import com.example.dosaaf_backend.exception.news.NewsNotFoundException;
import com.example.dosaaf_backend.model.NewsModel;
import com.example.dosaaf_backend.model.NewsPicModel;
import com.example.dosaaf_backend.repository.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private NewsPicService newsPicService;

    public NewsModel create(NewsEntity news) throws Exception {
        NewsEntity newsCreated = newsRepo.save(news);
        newsCreated.setPictures(newsPicService.createFromAlbumLink(news.getAlbumLink(), news.getId()));
        return NewsModel.toModel(newsCreated);
    }

    public NewsModel getOne(Long id) throws NewsNotFoundException {
        NewsEntity news = newsRepo.findById(id).orElse(null);

        if(news == null){
            throw new NewsNotFoundException("Новость с таким Id не была найдена");
        }

        return NewsModel.toModel(news);
    }


    public List<NewsModel> getAll() {
        List<NewsModel> newsModelList = new ArrayList<>();
        newsRepo.findAll().forEach(news ->{
            newsModelList.add(NewsModel.toModel(news));
        });

        return newsModelList;
    }

    public Long deleteNews(Long id){
        newsRepo.deleteById(id);
        return id;
    }

    public NewsModel archive(Long id){
        NewsEntity news = newsRepo.findById(id).get();
        news.setInArchive(!news.isInArchive());
        newsRepo.save(news);
        return NewsModel.toModel(news);
    }

    public NewsModel update(NewsEntity newsEntity){
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
        return NewsModel.toModel(news);
    }
}
