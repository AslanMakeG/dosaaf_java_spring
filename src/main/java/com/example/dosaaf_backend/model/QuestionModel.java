package com.example.dosaaf_backend.model;

import com.example.dosaaf_backend.entity.QuestionEntity;

import java.util.ArrayList;
import java.util.List;

public class QuestionModel {
    private Long id = null;
    private String name;
    private String description = null;
    private String questionType;
    private Long testId = null;
    private List<AnswerModel> answers;

    public static QuestionModel toModel(QuestionEntity questionEntity){
        QuestionModel questionModel = new QuestionModel();
        questionModel.setId(questionEntity.getId());
        questionModel.setName(questionEntity.getName());
        questionModel.setDescription(questionEntity.getDescription());
        questionModel.setQuestionType(questionEntity.getQuestionType().getName().name());
        questionModel.setTestId(questionEntity.getTest().getId());

        List<AnswerModel> answers = new ArrayList<>();
        questionEntity.getAnswers().forEach(answerEntity -> {
            answers.add(AnswerModel.toModel(answerEntity));
        });
        questionModel.setAnswers(answers);
        return questionModel;
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

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public List<AnswerModel> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerModel> answers) {
        this.answers = answers;
    }
}
