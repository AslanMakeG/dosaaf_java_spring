package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.AnswerEntity;
import com.example.dosaaf_backend.entity.QuestionEntity;
import com.example.dosaaf_backend.entity.TestEntity;
import com.example.dosaaf_backend.enums.EQuestionType;
import com.example.dosaaf_backend.exception.test.QuestionTypeNotFound;
import com.example.dosaaf_backend.model.AnswerModel;
import com.example.dosaaf_backend.model.QuestionModel;
import com.example.dosaaf_backend.model.TestModel;
import com.example.dosaaf_backend.repository.AnswerRepo;
import com.example.dosaaf_backend.repository.QuestionRepo;
import com.example.dosaaf_backend.repository.QuestionTypeRepo;
import com.example.dosaaf_backend.repository.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    @Autowired
    private TestRepo testRepo;
    @Autowired
    private QuestionRepo questionRepo;
    @Autowired
    private AnswerRepo answerRepo;
    @Autowired
    private QuestionTypeRepo questionTypeRepo;

    public TestModel create(TestModel testModel) throws QuestionTypeNotFound {
        TestEntity testEntity = new TestEntity();

        testEntity.setName(testModel.getName());
        testEntity.setDescription(testModel.getDescription());

        testEntity = testRepo.save(testEntity);

        List<QuestionEntity> questionEntities = new ArrayList<>();

         for (QuestionModel questionModel : testModel.getQuestions()){
            QuestionEntity questionEntity = new QuestionEntity();
            questionEntity.setName(questionModel.getName());
            questionEntity.setDescription(questionModel.getDescription());
            questionEntity.setTest(testEntity);
            questionEntity.setQuestionType(questionTypeRepo.findByName(EQuestionType.valueOf(questionModel.getQuestionType())).orElseThrow(
                    () -> new QuestionTypeNotFound("Тип вопроса не найден")
            ));

            questionEntity = questionRepo.save(questionEntity);

            List<AnswerEntity> answerEntities = new ArrayList<>();

            for(AnswerModel answerModel : questionModel.getAnswers()){
                AnswerEntity answerEntity = new AnswerEntity();
                answerEntity.setName(answerModel.getName());
                answerEntity.setRightAnswer(answerModel.getRightAnswer());
                answerEntity.setQuestion(questionEntity);
                answerEntities.add(answerRepo.save(answerEntity));
            }
            questionEntity.setAnswers(answerEntities);
            questionEntities.add(questionRepo.save(questionEntity));
         };
         testEntity.setQuestions(questionEntities);
         return TestModel.toModel(testRepo.save(testEntity));
    }
}
