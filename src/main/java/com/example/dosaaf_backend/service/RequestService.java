package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.RequestEntity;
import com.example.dosaaf_backend.enums.EStatus;
import com.example.dosaaf_backend.exception.request.RequestNotFoundException;
import com.example.dosaaf_backend.exception.request.RequestStatusNotFoundException;
import com.example.dosaaf_backend.exception.service.ServiceNotFoundException;
import com.example.dosaaf_backend.exception.user.UserNotFoundException;
import com.example.dosaaf_backend.model.RequestModel;
import com.example.dosaaf_backend.repository.RequestRepo;
import com.example.dosaaf_backend.repository.RequestStatusRepo;
import com.example.dosaaf_backend.repository.ServiceRepo;
import com.example.dosaaf_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RequestService {

    @Autowired
    private RequestRepo requestRepo;
    @Autowired
    private RequestStatusRepo requestStatusRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ServiceRepo serviceRepo;

    //Если пользователь авторизован то приходит только ID, если нет, то приходит ФИО и Email без ID
    public RequestModel create(RequestEntity requestEntity, Long userId, Long serviceId)
            throws RequestStatusNotFoundException, UserNotFoundException, ServiceNotFoundException {
        if(userId == -1){
            userId = null;
        }
        requestEntity.setStatus(requestStatusRepo.findByName(EStatus.STATUS_EXAMINE).orElseThrow(
                () -> new RequestStatusNotFoundException("Статус 'На рассмотрении' не найден")
        ));
        if(userId != null){
           requestEntity.setUser(userRepo.findById(userId).orElseThrow(
                   () -> new UserNotFoundException("Пользователь не найден")
           ));
        }
        requestEntity.setService(serviceRepo.findById(serviceId).orElseThrow(
                () -> new ServiceNotFoundException("Услуга не найдена")
        ));

        requestRepo.save(requestEntity);
        return RequestModel.toModel(requestEntity);
    }

    public Map<String, List<RequestModel>> getAll(){
        Map<String, List<RequestModel>> requestsSorted = new HashMap<String, List<RequestModel>>();

        List<RequestModel> requests = new ArrayList<>();

        requestRepo.findAll().forEach(request ->{
            requests.add(RequestModel.toModel(request));
        });

        //фильтрация элементов
        requestsSorted.put("examine", requests.stream().filter(request -> request.getStatus() == EStatus.STATUS_EXAMINE.name()).toList());
        requestsSorted.put("accepted", requests.stream().filter(request -> request.getStatus() == EStatus.STATUS_ACCEPTED.name()).toList());
        requestsSorted.put("rejected", requests.stream().filter(request -> request.getStatus() == EStatus.STATUS_REJECTED.name()).toList());

        return requestsSorted;
    }

    public RequestModel reject(Long id) throws RequestNotFoundException, RequestStatusNotFoundException {
        RequestEntity request = requestRepo.findById(id).orElseThrow(
                () -> new RequestNotFoundException("Заявка не найдена")
        );

        request.setStatus(requestStatusRepo.findByName(EStatus.STATUS_REJECTED).orElseThrow(
                () -> new RequestStatusNotFoundException("Статус ОТКЛОНЕНО не найден")
        ));

        return RequestModel.toModel(requestRepo.save(request));
    }

    public RequestModel accept(Long id) throws RequestNotFoundException, RequestStatusNotFoundException {
        RequestEntity request = requestRepo.findById(id).orElseThrow(
                () -> new RequestNotFoundException("Заявка не найдена")
        );

        request.setStatus(requestStatusRepo.findByName(EStatus.STATUS_ACCEPTED).orElseThrow(
                () -> new RequestStatusNotFoundException("Статус ПРИНЯТО не найден")
        ));

        return RequestModel.toModel(requestRepo.save(request));
    }
}
