package com.example.dosaaf_backend.model;

import com.example.dosaaf_backend.entity.TestEntity;

import java.util.ArrayList;
import java.util.List;

public class TestModel {
    private Long id = null;
    private String name;
    private String description = null;
    private List<QuestionModel> questions;

    public static TestModel toModel(TestEntity testEntity){
        TestModel testModel = new TestModel();
        testModel.setId(testEntity.getId());
        testModel.setName(testEntity.getName());
        testModel.setDescription(testEntity.getDescription());

        List<QuestionModel> questions = new ArrayList<>();
        testEntity.getQuestions().forEach(questionEntity -> {
            questions.add(QuestionModel.toModel(questionEntity));
        });
        testModel.setQuestions(questions);
        return testModel;
    }

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

    public List<QuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionModel> questions) {
        this.questions = questions;
    }
}
