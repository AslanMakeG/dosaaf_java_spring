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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    private SmtpMailSender mailSender;

    @Value("${dosaaf_backend.email.admin}")
    private String adminEmail;

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

        requestEntity = requestRepo.save(requestEntity);

        String message = String.format(
                "Новая заявка от: %s на услугу %s \n" +
                        "Email: %s \n" +
                        "Номер телефона: %s",
                requestEntity.getUser() == null ?
                (requestEntity.getUserSurname() + " " + requestEntity.getUserName() + " "
                        + (requestEntity.getUserPatronymic() == null ? "" : requestEntity.getUserPatronymic()))
                : (requestEntity.getUser().getSurname() + " " + requestEntity.getUser().getName() + " "
                        + (requestEntity.getUser().getPatronymic() == null ? "" : requestEntity.getUser().getPatronymic())),
                requestEntity.getService().getName(),
                requestEntity.getUserEmail(),
                requestModel.getUserPhoneNumber()

        );

        mailSender.send(adminEmail, "Новая заявка", message);

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

    public RequestModel notify(Long id) throws RequestNotFoundException, RequestStatusNotFoundException {
        RequestEntity request = requestRepo.findById(id).orElseThrow(
                () -> new RequestNotFoundException("Заявка не найдена")
        );

        String message = String.format(
                "Статус заявки №%s изменен.\n" +
                        "Ваша заявка была %s администратором.",
                request.getId(),
                request.getStatus().getName() == EStatus.STATUS_ACCEPTED ? "принята" : "отклонена"
        );

        if(request.getUser() != null){
            mailSender.send(request.getUser().getEmail(), "Изменен статус заявки", message);
        }
        else {
            mailSender.send(request.getUserEmail(), "Изменен статус заявки", message);
        }

        return RequestModel.toModel(requestRepo.save(request));
    }

    public List<RequestModel> getAllFromUser(String email) throws UserEmailNotFoundException {
        List<RequestModel> requestModels = new ArrayList<>();
        UserEntity user = userRepo.findByEmail(email).orElseThrow(
                () -> new UserEmailNotFoundException("Пользователь не найден")
        );
        requestRepo.findByUserEmail(email, Sort.by(Sort.Direction.ASC, "date")).forEach(requestEntity -> {
            requestModels.add(RequestModel.toModel(requestEntity));
        });

        return requestModels;
    }

    public RequestModel getLast(String email){
        return RequestModel.toModel(requestRepo.findFirstByUserEmail(email, Sort.by(Sort.Direction.DESC, "date")).orElse(
                null
        ));
    }

    public Long getRequestsCountByDate(String date) throws Exception {
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        Matcher matcher = pattern.matcher(date);
        if(!matcher.find()){
            throw new Exception("Неправильный формат даты");
        }
        return requestRepo.countByDate(date);
    }
}
