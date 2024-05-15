package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.NewsEntity;
import com.example.dosaaf_backend.entity.NewsPicEntity;
import com.example.dosaaf_backend.exception.news.NewsNotFoundException;
import com.example.dosaaf_backend.model.NewsModel;
import com.example.dosaaf_backend.model.NewsPicModel;
import com.example.dosaaf_backend.repository.NewsPicRepo;
import com.example.dosaaf_backend.repository.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class NewsService {
    @Autowired
    private NewsRepo newsRepo;
    @Autowired
    private NewsPicRepo newsPicRepo;

    @Autowired
    private NewsPicService newsPicService;

    public NewsModel create(NewsModel news) throws Exception {
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setTitle(news.getTitle());
        newsEntity.setContent(news.getContent());
        newsEntity.setCreationDateTime(news.getCreationDateTime());
        newsEntity = newsRepo.save(newsEntity);

        if(news.getPictures() != null && !news.getPictures().isEmpty()){
            List<NewsPicEntity> newsPicEntities = new ArrayList<>();
            for(NewsPicModel newsPicModel : news.getPictures()){
                NewsPicEntity newsPicEntity = new NewsPicEntity();
                newsPicEntity.setPictureLink(newsPicModel.getPictureLink());
                newsPicEntity.setMainPicture(newsPicModel.isMainPicture());
                newsPicEntity.setNews(newsEntity);
                newsPicEntities.add(newsPicRepo.save(newsPicEntity));
            }
            newsEntity.setPictures(newsPicEntities);
        }
        else if(news.getAlbumLink() != null && !Objects.equals(news.getAlbumLink(), "")){
            newsEntity.setPictures(newsPicService.createFromAlbumLink(news.getAlbumLink(), newsEntity.getId()));
        }

        newsEntity.setAlbumLink(news.getAlbumLink());
        newsEntity = newsRepo.save(newsEntity);
        return NewsModel.toModel(newsEntity);
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

    public List<NewsModel> getByPage(Integer page, Integer limit, List<String> query) {
        List<NewsEntity> newsEntities;

        if(query != null){
            HashSet<NewsEntity> newsEntityHashSet = new HashSet<>();
            for(String q : query){
                newsEntityHashSet.addAll(newsRepo.findAll((page * limit) - limit, limit, q));
            }
            newsEntities = newsEntityHashSet.stream().toList();
        }
        else{
            newsEntities = newsRepo.findAll((page * limit) - limit, limit);
        }

        List<NewsModel> newsModelList = new ArrayList<>();
        newsEntities.forEach(news ->{
            newsModelList.add(NewsModel.toModel(news));
        });

        return newsModelList;
    }

    public Long getCount(List<String> query) {
        if(query != null){
            HashSet<NewsEntity> newsEntityHashSet = new HashSet<>();
            for(String q : query){
                newsEntityHashSet.addAll(newsRepo.findByQuery(q));
            }
            return (long) newsEntityHashSet.size();
        }
        return newsRepo.count();
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
