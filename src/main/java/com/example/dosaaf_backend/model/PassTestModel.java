package com.example.dosaaf_backend.model;


import java.util.List;

public class PassTestModel {
    private Long id; //id теста
    private List<PastTestQuestionModel> questions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PastTestQuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<PastTestQuestionModel> questions) {
        this.questions = questions;
    }
}
