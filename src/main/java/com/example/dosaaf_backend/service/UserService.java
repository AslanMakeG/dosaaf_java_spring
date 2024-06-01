package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.RoleEntity;
import com.example.dosaaf_backend.enums.ERole;
import com.example.dosaaf_backend.exception.user.*;
import com.example.dosaaf_backend.entity.UserEntity;
import com.example.dosaaf_backend.model.ResetPasswordRequest;
import com.example.dosaaf_backend.model.UserModel;
import com.example.dosaaf_backend.repository.RoleRepo;
import com.example.dosaaf_backend.repository.UserRepo;
import com.example.dosaaf_backend.security.JwtUtils;
import com.example.dosaaf_backend.security.Pojo.JwtResponse;
import com.example.dosaaf_backend.security.Pojo.LoginRequest;
import com.example.dosaaf_backend.security.Pojo.SingupRequest;
import com.example.dosaaf_backend.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SmtpMailSender mailSender;

    @Value("${dosaaf_backend.server.backend_address}")
    private String serverAddress;

    @Value("${dosaaf_backend.server.frontend_address}")
    private String clientAddress;

    public UserModel getUserInfoByEmail(String email) throws UserEmailNotFoundException {
        UserEntity user = userRepo.findByEmail(email).orElseThrow(
                () -> new UserEmailNotFoundException("Пользователь с таким Email не найден")
        );

        return UserModel.toModel(user);
    }

    public UserEntity create(SingupRequest singupRequest) throws UserAlreadyExsistsException, RoleNotFoundException { //изменить пароль на зашифрованный
        if(userRepo.existsByEmail(singupRequest.getEmail())){
            throw new UserAlreadyExsistsException("пользователь с таким email уже существует");
        }

        UserEntity user = new UserEntity();
        user.setEmail(singupRequest.getEmail());
        user.setName(singupRequest.getName());
        user.setSurname(singupRequest.getSurname());
        user.setPatronymic(singupRequest.getPatronymic());
        user.setPassword(passwordEncoder.encode(singupRequest.getPassword()));
        user.setRegistrationDate(new Date());
        user.setSubscribedForNews(singupRequest.isSubscribedForNews());

        Set<String> reqRoles = singupRequest.getRoles();
        Set<RoleEntity> roles = new HashSet<>();

        if(reqRoles == null){
            RoleEntity userRole = roleRepo.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RoleNotFoundException("Роль USER не найдена"));
            roles.add(userRole);
        }
        else{
            RoleEntity userRole;
            switch (reqRoles.stream().toList().get(0)){
                case "user":
                    userRole = roleRepo.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RoleNotFoundException("Роль USER не найдена"));
                    roles.add(userRole);
                    break;
                case "admin":
                    userRole = roleRepo.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RoleNotFoundException("Роль ADMIN не найдена"));
                    roles.add(userRole);
                    break;
                default:
                    userRole = roleRepo.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RoleNotFoundException("Роль USER не найдена"));
                    roles.add(userRole);
                    break;
            }
        }

        user.setRoles(roles);
        user.setActivationCode(UUID.randomUUID().toString());

        String message = String.format(
                "Здравствуйте, %s! \n" +
                        "Для продолжения регистрации просим Вас перейти по ссылке: %s/activate?uuid=%s",
                (user.getSurname() + " " + user.getName() + " " + (user.getPatronymic() == null ? "" : user.getPatronymic())),
                clientAddress,
                user.getActivationCode()
        );

        mailSender.send(user.getEmail(), "Подтверждение почты", message);

        return userRepo.save(user);
    }
    public JwtResponse auth(LoginRequest loginRequest) throws UserEmailNotFoundException, UserNotActivatedException { //изменить пароль на зашифрованный

        UserEntity user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new UserEmailNotFoundException("пользователь с таким email не зарегистрирован")
        );

        if(user.getActivationCode() != null){
            throw new UserNotActivatedException("email пользователя не подтвержден");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).toList();

        return new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getName(),
                userDetails.getSurname(),
                userDetails.getPatronymic(),
                roles);
    }

    public UserModel activate(String code) throws UserNotFoundException {
        UserEntity user = userRepo.findByActivationCode(code).orElseThrow(
                () -> new UserNotFoundException("Пользователь не найден")
        );

        user.setActivationCode(null);

        userRepo.save(user);

        return UserModel.toModel(user);
    }

    public UserModel forgotPassword(String email) throws UserEmailNotFoundException {
        UserEntity user = userRepo.findByEmail(email).orElseThrow(
                () -> new UserEmailNotFoundException("Пользователь не найден")
        );

        user.setForgotPasswordCode(UUID.randomUUID().toString());

        String message = String.format(
                "Здравствуйте, %s! \n" +
                        "Чтобы продолжить восстановление пароля, просим Вас перейти по ссылке: %s/password_recovery?uuid=%s",
                (user.getSurname() + " " + user.getName() + (user.getPatronymic() == null ? "" : " " + user.getPatronymic())),
                clientAddress,
                user.getForgotPasswordCode()
        );

        mailSender.send(user.getEmail(), "Восстановление пароля", message);

        return UserModel.toModel(userRepo.save(user));
    }

    public UserModel resetPassword(ResetPasswordRequest resetPasswordRequest) throws UserNotFoundException {
        UserEntity user = userRepo.findByForgotPasswordCode(resetPasswordRequest.getForgotPasswordCode()).orElseThrow(
                () -> new UserNotFoundException("Пользователь не найден")
        );

        user.setForgotPasswordCode(null);
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));

        return UserModel.toModel(userRepo.save(user));
    }

    public UserModel unsubscribe(String email) throws UserEmailNotFoundException {
        UserEntity user = userRepo.findByEmail(email).orElseThrow(
                () -> new UserEmailNotFoundException("Пользователь не найден")
        );

        user.setSubscribedForNews(false);

        return UserModel.toModel(userRepo.save(user));
    }

    public UserModel subscribe(String email) throws UserEmailNotFoundException {
        UserEntity user = userRepo.findByEmail(email).orElseThrow(
                () -> new UserEmailNotFoundException("Пользователь не найден")
        );

        user.setSubscribedForNews(true);

        return UserModel.toModel(userRepo.save(user));
    }
}
