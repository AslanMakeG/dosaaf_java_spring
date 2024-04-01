package com.example.dosaaf_backend.security;

import com.example.dosaaf_backend.entity.RoleEntity;
import com.example.dosaaf_backend.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
    private Date registrationDate;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String name, String surname, String patronymic, String email, String password, Date registrationDate, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(UserEntity user){
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
            user.getId(),
            user.getName(),
            user.getSurname(),
            user.getPatronymic(),
            user.getEmail(),
            user.getPassword(),
            user.getRegistrationDate(),
            authorities
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
