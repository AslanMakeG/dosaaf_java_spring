package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.RequestEntity;
import com.example.dosaaf_backend.entity.UserEntity;
import com.example.dosaaf_backend.enums.EStatus;
import com.example.dosaaf_backend.exception.request.RequestNotFoundException;
import com.example.dosaaf_backend.exception.request.RequestStatusNotFoundException;
import com.example.dosaaf_backend.exception.service.ServiceNotFoundException;
import com.example.dosaaf_backend.exception.user.UserEmailNotFoundException;
import com.example.dosaaf_backend.exception.user.UserNotFoundException;
import com.example.dosaaf_backend.model.RequestCreationModel;
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
    public RequestModel create(RequestCreationModel requestModel, String email, Long serviceId)
            throws RequestStatusNotFoundException, UserNotFoundException, ServiceNotFoundException {

        RequestEntity requestEntity = new RequestEntity();

        requestEntity.setStatus(requestStatusRepo.findByName(EStatus.STATUS_EXAMINE).orElseThrow(
                () -> new RequestStatusNotFoundException("Статус 'На рассмотрении' не найден")
        ));
        if(email != null){
            UserEntity user = userRepo.findByEmail(email).orElseThrow(
                    () -> new UserNotFoundException("Пользователь не найден")
            );
           requestEntity.setUser(user);
           requestEntity.setUserEmail(user.getEmail());
        }
        else{
            requestEntity.setUserName(requestModel.getUserName());
            requestEntity.setUserSurname(requestModel.getUserSurname());
            requestEntity.setUserPatronymic(requestModel.getUserPatronymic());
            requestEntity.setUserEmail(requestModel.getUserEmail());
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

    public List<RequestModel> getAllFromUser(String email) throws UserEmailNotFoundException {
        List<RequestModel> requestModels = new ArrayList<>();
        UserEntity user = userRepo.findByEmail(email).orElseThrow(
                () -> new UserEmailNotFoundException("Пользователь не найден")
        );
        requestRepo.findByUserEmail(email).forEach(requestEntity -> {
            requestModels.add(RequestModel.toModel(requestEntity));
        });

        return requestModels;
    }
}
