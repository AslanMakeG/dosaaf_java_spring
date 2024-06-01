package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmtpMailSender {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void send(String emailTo, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    public void sendNewsNotification(List<UserEntity> users, Long newsId){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setSubject("Новая новость");
        mailMessage.setText(String.format(
                "У нас новая новость! \n" +
                        "Вы можете посмотреть ее по ссылке http://localhost:3000/new?newsId=%s",
                newsId
        ));

        for(UserEntity user : users){
            mailMessage.setTo(user.getEmail());
            mailSender.send(mailMessage);
        }
    }
}
