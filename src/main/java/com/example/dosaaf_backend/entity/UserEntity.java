package com.example.dosaaf_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String patronymic;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date registrationDate;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    public UserEntity(String name, String surname, String patronymic, String email, String password, Date registrationDate) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
    }

    public UserEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
