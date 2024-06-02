package com.example.dosaaf_backend.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="test_results")
public class TestResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date = new Date();
    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestEntity test;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "testResult")
    private List<UserAnswerEntity> userAnswers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TestEntity getTest() {
        return test;
    }

    public void setTest(TestEntity test) {
        this.test = test;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<UserAnswerEntity> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<UserAnswerEntity> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
