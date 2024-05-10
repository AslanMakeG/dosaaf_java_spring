package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.AnswerEntity;
import com.example.dosaaf_backend.entity.QuestionEntity;
import com.example.dosaaf_backend.entity.TestEntity;
import com.example.dosaaf_backend.enums.EQuestionType;
import com.example.dosaaf_backend.exception.test.QuestionTypeNotFound;
import com.example.dosaaf_backend.exception.test.TestNotFound;
import com.example.dosaaf_backend.model.AnswerModel;
import com.example.dosaaf_backend.model.QuestionModel;
import com.example.dosaaf_backend.model.TestModel;
import com.example.dosaaf_backend.repository.AnswerRepo;
import com.example.dosaaf_backend.repository.QuestionRepo;
import com.example.dosaaf_backend.repository.QuestionTypeRepo;
import com.example.dosaaf_backend.repository.TestRepo;
import jakarta.transaction.Transactional;
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

    private TestEntity createOrUpdate(TestEntity testEntity, TestModel testModel, boolean isUpdate) throws QuestionTypeNotFound {
        testEntity.setName(testModel.getName());
        testEntity.setDescription(testModel.getDescription());

        List<QuestionEntity> questionEntities = new ArrayList<>();

        //Если создаем тест, то предварительно сохраним, чтобы у него был Id
        if(!isUpdate){
            testEntity = testRepo.save(testEntity);
        }

        //Если тест на обновлении, то надо удалить все его предыдущие вопросы
        if(isUpdate){
            questionRepo.deleteByTestId(testEntity.getId());
        }

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
        return testEntity;
    }

    public TestModel create(TestModel testModel) throws QuestionTypeNotFound {
        TestEntity testEntity = new TestEntity();
        testEntity = createOrUpdate(testEntity, testModel, false);
        return TestModel.toModel(testRepo.save(testEntity));
    }

    public Long delete(Long id){
        testRepo.deleteById(id);
        return id;
    }

    public List<TestModel> getAll(){
        List<TestModel> tests = new ArrayList<>();
        testRepo.findAll().forEach(testEntity -> {
            tests.add(TestModel.toModel(testEntity));
        });
        return tests;
    }

    public TestModel getOne(Long id) throws TestNotFound {
        return TestModel.toModel(testRepo.findById(id).orElseThrow(
                () -> new TestNotFound("Тест не найден")
        ));
    }

    public TestModel update(TestModel testModel) throws TestNotFound, QuestionTypeNotFound {
        TestEntity testEntity = testRepo.findById(testModel.getId()).orElseThrow(
                () -> new TestNotFound("Тест не найден")
        );
        testEntity = createOrUpdate(testEntity, testModel, true);
        return TestModel.toModel(testRepo.save(testEntity));
    }
}
