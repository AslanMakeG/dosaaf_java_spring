package com.example.dosaaf_backend.model;

import java.util.List;

public class PastTestQuestionModel {
    private Long id;
    private List<Long> answersId = null;
    private String answerText = null;
    private Boolean right;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getAnswersId() {
        return answersId;
    }

    public void setAnswersId(List<Long> answersId) {
        this.answersId = answersId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Boolean getRight() {
        return right;
    }

    public void setRight(Boolean right) {
        this.right = right;
    }
}
