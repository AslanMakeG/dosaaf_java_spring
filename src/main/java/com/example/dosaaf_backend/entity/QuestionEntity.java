package com.example.dosaaf_backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "questions")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "questionType_id")
    private QuestionTypeEntity questionType;
    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestEntity test;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<AnswerEntity> answers;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<UserAnswerEntity> userAnswers;

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

    public QuestionTypeEntity getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionTypeEntity questionType) {
        this.questionType = questionType;
    }

    public TestEntity getTest() {
        return test;
    }

    public void setTest(TestEntity test) {
        this.test = test;
    }

    public List<AnswerEntity> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerEntity> answers) {
        this.answers = answers;
    }

    public List<UserAnswerEntity> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<UserAnswerEntity> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
