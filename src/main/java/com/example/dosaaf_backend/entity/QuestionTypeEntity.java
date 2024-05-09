package com.example.dosaaf_backend.entity;

import com.example.dosaaf_backend.enums.EQuestionType;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "question_type")
public class QuestionTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;
    private EQuestionType name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionType")
    private List<QuestionEntity> questions;

    public QuestionTypeEntity(EQuestionType name) {
        this.name = name;
    }

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public EQuestionType getName() {
        return name;
    }

    public void setName(EQuestionType name) {
        this.name = name;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }
}
