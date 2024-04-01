package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.RoleEntity;
import com.example.dosaaf_backend.enums.ERole;
import com.example.dosaaf_backend.exception.RoleNotFoundException;
import com.example.dosaaf_backend.exception.UserAlreadyExsistsException;
import com.example.dosaaf_backend.entity.UserEntity;
import com.example.dosaaf_backend.exception.UserEmailNotFoundException;
import com.example.dosaaf_backend.repository.RoleRepo;
import com.example.dosaaf_backend.repository.UserRepo;
import com.example.dosaaf_backend.security.JwtUtils;
import com.example.dosaaf_backend.security.Pojo.JwtResponse;
import com.example.dosaaf_backend.security.Pojo.LoginRequest;
import com.example.dosaaf_backend.security.Pojo.SingupRequest;
import com.example.dosaaf_backend.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public UserEntity getUserByEmail(String email) throws UserEmailNotFoundException {
        UserEntity user = userRepo.findByEmail(email).orElseThrow();
        if(user == null){
            throw new UserEmailNotFoundException("Пользователь с таким Email не найден");
        }
        return user;
    }

    public UserEntity create(SingupRequest singupRequest) throws UserAlreadyExsistsException, RoleNotFoundException { //изменить пароль на зашифрованный
        if(userRepo.existsByEmail(singupRequest.getEmail())){
            throw new UserAlreadyExsistsException("Пользователь с таким email уже существует");
        }

        UserEntity user = new UserEntity();
        user.setEmail(singupRequest.getEmail());
        user.setName(singupRequest.getName());
        user.setSurname(singupRequest.getSurname());
        user.setPatronymic(singupRequest.getPatronymic());
        user.setPassword(passwordEncoder.encode(singupRequest.getPassword()));
        user.setRegistrationDate(new Date());

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
        return userRepo.save(user);
    }
    public JwtResponse auth(LoginRequest loginRequest) { //изменить пароль на зашифрованный
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
}
