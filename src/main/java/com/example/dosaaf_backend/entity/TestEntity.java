package com.example.dosaaf_backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tests")
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "test")
    private List<QuestionEntity> questions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "test")
    private List<TestResultEntity> userResults;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }
}
