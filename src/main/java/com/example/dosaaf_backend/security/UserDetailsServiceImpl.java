package com.example.dosaaf_backend.security;

import com.example.dosaaf_backend.entity.UserEntity;
import com.example.dosaaf_backend.exception.UserEmailNotFoundException;
import com.example.dosaaf_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователя с таким Email не существует"));
        return UserDetailsImpl.build(user);
    }

    public UserDetails loadUserByEmail(String email) throws UserEmailNotFoundException {
        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserEmailNotFoundException("Пользователя с таким Email не существует"));
        return UserDetailsImpl.build(user);
    }
}
