package com.example.dosaaf_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user_answers")
public class UserAnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "testResult_id")
    private TestResultEntity testResult;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;
    private Boolean rightAnswer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TestResultEntity getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResultEntity testResult) {
        this.testResult = testResult;
    }

    public Boolean getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Boolean rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }
}
