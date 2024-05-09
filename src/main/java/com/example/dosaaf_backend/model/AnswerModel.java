package com.example.dosaaf_backend.model;

import com.example.dosaaf_backend.entity.AnswerEntity;

public class AnswerModel {
    private Long id = null;
    private String name;
    private Boolean rightAnswer;
    private Long questionId = null;

    public static AnswerModel toModel(AnswerEntity answerEntity){
        AnswerModel answerModel = new AnswerModel();
        answerModel.setId(answerEntity.getId());
        answerModel.setName(answerEntity.getName());
        answerModel.setRightAnswer(answerEntity.getRightAnswer());
        answerModel.setQuestionId(answerEntity.getQuestion().getId());
        return answerModel;
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

    public Boolean getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Boolean rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
