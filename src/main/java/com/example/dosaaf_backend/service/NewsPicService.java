package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.NewsEntity;
import com.example.dosaaf_backend.entity.NewsPicEntity;
import com.example.dosaaf_backend.exception.news.NewsNotFoundException;
import com.example.dosaaf_backend.model.NewsPicModel;
import com.example.dosaaf_backend.repository.NewsPicRepo;
import com.example.dosaaf_backend.repository.NewsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NewsPicService {
    @Autowired
    private NewsPicRepo newsPicRepo;
    @Autowired
    private NewsRepo newsRepo;

    @Value("${dosaaf_backend.vk.service_key}")
    private String vkServiceKey;


    private List<NewsPicModel> getImagesFromAlbum(String albumLink) throws Exception {
        if(!albumLink.contains("vk.com") || !albumLink.contains("album") ){
            throw new Exception("Неверная ссылка на альбом");
        }

        String ownerId = albumLink.substring(albumLink.indexOf('-'), albumLink.indexOf('_'));
        String albumId = albumLink.substring(albumLink.indexOf('_') + 1);

        URL url = new URL(String.format("https://api.vk.com/method/photos.get?v=5.199&owner_id=%s&album_id=%s&access_token=%s&count=1000",
                ownerId, albumId, vkServiceKey));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if(responseCode != 200){
            throw new Exception("Ошибка при получении фотографий с альбома");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        Map<String, Object> mapping = new ObjectMapper().readValue(response.toString(), HashMap.class);

        Map<String, Object> items = (Map<String, Object>) mapping.get("response");

        List<Map<String, Object>> responseItems = (List<Map<String, Object>>) items.get("items");

        List<NewsPicModel> pictures = new ArrayList<>();

        for(int i = 0; i < responseItems.size(); i++){
            List<Map<String, Object>> sizes = (List<Map<String, Object>>) responseItems.get(i).get("sizes");
            Optional<Map<String, Object>> result = sizes.stream().reduce((Map<String, Object> a, Map<String, Object> b) ->
                    Integer.parseInt(a.get("height").toString()) * Integer.parseInt(a.get("width").toString()) >
                            Integer.parseInt(b.get("height").toString()) * Integer.parseInt(b.get("width").toString()) ? a : b);

            if(result.isPresent()){
                NewsPicModel newsPicModel = new NewsPicModel();
                newsPicModel.setMainPicture(i == 0);
                newsPicModel.setPictureLink((String) result.get().get("url"));
                pictures.add(newsPicModel);
            }
        }
        return pictures;
    }

    public List<NewsPicEntity> createFromAlbumLink(String albumLink, Long newsId) throws Exception {
        NewsEntity newsEntity = newsRepo.findById(newsId).orElseThrow(
                () -> new NewsNotFoundException("Новость не найдена")
        );

        List<NewsPicModel> newsPictures = getImagesFromAlbum(albumLink);

        List<NewsPicEntity> picEntities = new ArrayList<>();

        for(NewsPicModel picture : newsPictures){
            NewsPicEntity newsPicEntity = new NewsPicEntity();

            newsPicEntity.setNews(newsEntity);
            newsPicEntity.setMainPicture(picture.isMainPicture());
            newsPicEntity.setPictureLink(picture.getPictureLink());
            picEntities.add(newsPicRepo.save(newsPicEntity));
        }

        return picEntities;
    }

    public List<NewsPicModel> getFromAlbumLink(String albumLink) throws Exception {
        return getImagesFromAlbum(albumLink);
    }

    public NewsPicEntity create(NewsPicEntity picture, Long newsId) throws NewsNotFoundException {
        NewsEntity news = newsRepo.findById(newsId).orElseThrow(
                () -> new NewsNotFoundException("Новость не найдена")
        );
        picture.setNews(news);
        return newsPicRepo.save(picture);
    }

    public NewsPicModel makeMainPicture(Long id){
        NewsPicEntity picture = newsPicRepo.findById(id).get();
        List<NewsPicEntity> pictures = newsPicRepo.findByNewsId(picture.getNews().getId());
        for(NewsPicEntity i: pictures){
            i.setMainPicture(i.getId() == id); //установка true/false в поле главной картинки
            newsPicRepo.save(i);
        }
        return NewsPicModel.toModel(picture);
    }
}
