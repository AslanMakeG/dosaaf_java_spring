package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.NewsEntity;
import com.example.dosaaf_backend.entity.NewsPicEntity;
import com.example.dosaaf_backend.exception.news.NewsNotFoundException;
import com.example.dosaaf_backend.exception.news.NewsPictureNotFound;
import com.example.dosaaf_backend.model.NewsModel;
import com.example.dosaaf_backend.model.NewsPicModel;
import com.example.dosaaf_backend.repository.NewsPicRepo;
import com.example.dosaaf_backend.repository.NewsRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

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
        NewsEntity news = newsRepo.findById(id).orElseThrow(
                () -> new NewsNotFoundException("Новость с таким Id не была найдена")
        );
        List<NewsPicEntity> newsPicEntities = new ArrayList<>(news.getPictures());
        newsPicEntities.sort(Comparator.comparing(o -> o.getId()));
        news.setPictures(newsPicEntities);

        return NewsModel.toModel(news);
    }


    public List<NewsModel> getAll() {
        List<NewsModel> newsModelList = new ArrayList<>();
        newsRepo.findAll().forEach(news ->{
            newsModelList.add(NewsModel.toModel(news));
        });

        return newsModelList;
    }

    public List<NewsModel> getByPage(Integer page, Integer limit, List<String> query, String dateSort) {
        List<NewsEntity> newsEntities;

        if(!Objects.equals(dateSort, "asc") && !Objects.equals(dateSort, "desc")){
            dateSort = "desc";
        }

        if(query != null){
            newsEntities = newsRepo.findAll(Sort.by(dateSort.toLowerCase().equals("asc") ?
                    Sort.Direction.ASC : Sort.Direction.DESC,
                    "creationDateTime"));

            for(String queryWord : query) {
                newsEntities = newsEntities.stream().filter(newsEntity -> newsEntity.getTitle()
                        .toLowerCase()
                        .contains(queryWord.toLowerCase()) || newsEntity.getContent()
                                .toLowerCase()
                                .contains(queryWord.toLowerCase()))
                        .toList();

                Integer indexFrom = (page * limit) - limit;
                Integer indexTo = page * limit;

                if(indexTo >= newsEntities.size()){
                    indexTo = newsEntities.size();
                }

                newsEntities = newsEntities.subList(indexFrom, indexTo);
            }
        }
        else{
            newsEntities = dateSort.toLowerCase().equals("asc") ?
                    newsRepo.findAllByDateASC((page * limit) - limit, limit)
                    : newsRepo.findAllByDateDESC((page * limit) - limit, limit);

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

    public NewsModel archive(Long id) throws NewsNotFoundException {
        NewsEntity news = newsRepo.findById(id).orElseThrow(
                () -> new NewsNotFoundException("Новость не найдена")
        );
        news.setInArchive(!news.isInArchive());
        newsRepo.save(news);
        return NewsModel.toModel(news);
    }

    public NewsModel update(NewsModel newsModel) throws NewsNotFoundException, NewsPictureNotFound {
        NewsEntity news = newsRepo.findById(newsModel.getId()).orElseThrow(
                () -> new NewsNotFoundException("Новость не найдена")
        );

        if(newsModel.getPictures() != null && !newsModel.getPictures().isEmpty()){
            List<NewsPicEntity> newsModelPictures = new ArrayList<>();

            for(NewsPicModel newsPicModel : newsModel.getPictures()){
                NewsPicEntity newsPicEntity = new NewsPicEntity();
                if(newsPicModel.getId() != null){
                    newsPicEntity = newsPicRepo.findById(newsPicModel.getId()).orElseThrow(
                            () -> new NewsPictureNotFound("Фотография новости не найдена")
                    );
                }
                else{
                    newsPicEntity.setPictureLink(newsPicModel.getPictureLink());
                    newsPicEntity.setNews(news);
                    newsPicEntity = newsPicRepo.save(newsPicEntity);
                }

                newsPicEntity.setMainPicture(newsPicModel.isMainPicture());

                newsModelPictures.add(newsPicEntity);
            }
            news.setPictures(newsModelPictures);
            newsPicRepo.deleteByNewsId(newsModel.getId());
        }
        news.setContent(newsModel.getContent());
        news.setTitle(newsModel.getTitle());
        news.setAlbumLink(newsModel.getAlbumLink());
        news = newsRepo.save(news);
        return NewsModel.toModel(news);
    }
}
