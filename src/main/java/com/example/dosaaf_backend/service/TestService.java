package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.*;
import com.example.dosaaf_backend.enums.EQuestionType;
import com.example.dosaaf_backend.exception.test.QuestionNotFound;
import com.example.dosaaf_backend.exception.test.QuestionTypeNotFound;
import com.example.dosaaf_backend.exception.test.TestNotFound;
import com.example.dosaaf_backend.exception.user.UserEmailNotFoundException;
import com.example.dosaaf_backend.model.*;
import com.example.dosaaf_backend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private TestResultRepo testResultRepo;
    @Autowired
    private UserAnswerRepo userAnswerRepo;
    @Autowired
    private UserRepo userRepo;

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

    public PassTestModel pass(PassTestModel passTestModel, String userEmail) throws QuestionNotFound, TestNotFound, UserEmailNotFoundException {
        TestResultEntity testResult = new TestResultEntity();
        testResult.setUser(userRepo.findByEmail(userEmail).orElseThrow(
                () -> new UserEmailNotFoundException("Пользователь не найден")
        ));

        List<UserAnswerEntity> userAnswerEntities = new ArrayList<>();

        testResult.setTest(testRepo.findById(passTestModel.getId()).orElseThrow(
                () -> new TestNotFound("Тест не найден")
        ));

        testResult = testResultRepo.save(testResult);

        for(PastTestQuestionModel question : passTestModel.getQuestions()){
            UserAnswerEntity userAnswerEntity = new UserAnswerEntity();

            QuestionEntity questionEntity = questionRepo.findById(question.getId()).orElseThrow(
                    () -> new QuestionNotFound("Вопрос не найден")
            );

            userAnswerEntity.setQuestion(questionEntity);

            List<AnswerEntity> answerEntities = questionEntity.getAnswers();

            if(questionEntity.getQuestionType().getName() == EQuestionType.TYPE_SIMPLE){
                if(question.getAnswersId().size() == 0){
                    question.setRight(false);
                }
                else{
                    answerEntities = answerEntities.stream().filter(
                            answerEntity -> answerEntity.getId() == question.getAnswersId().get(0)).toList();
                    if(answerEntities.size() == 0){
                        question.setRight(false);
                    }
                    else{
                        question.setRight(answerEntities.get(0).getRightAnswer());
                    }
                }
            }

            if(questionEntity.getQuestionType().getName() == EQuestionType.TYPE_MULTIPLE){
                answerEntities = answerEntities.stream().filter(
                        answerEntity -> answerEntity.getRightAnswer()).toList();

                if(answerEntities.size() == 0){
                    question.setRight(false);
                }
                else{
                    boolean right = true;

                    for(AnswerEntity answer : answerEntities){
                        if(!question.getAnswersId().contains(answer.getId())){
                            right = false;
                            break;
                        }
                    }
                    question.setRight(right);
                }
            }

            if(questionEntity.getQuestionType().getName() == EQuestionType.TYPE_TEXT){
                if(question.getAnswerText() == null){
                    question.setRight(false);
                }
                if(Objects.equals(question.getAnswerText().toLowerCase(), answerEntities.get(0).getName().toLowerCase())){
                    question.setRight(true);
                }
                else{
                    question.setRight(false);
                }
            }

            userAnswerEntity.setRightAnswer(question.getRight());
            userAnswerEntity.setTestResult(testResult);
            userAnswerEntities.add(userAnswerRepo.save(userAnswerEntity));
        }

        testResult.setUserAnswers(userAnswerEntities);
        testResultRepo.save(testResult);
        return passTestModel;
    }

    public Float percent(Long id, String userEmail) {
        List<TestResultEntity> testResultEntities = testResultRepo.findByTestId(id);

        if(testResultEntities.size() == 0){
            return 0f;
        }

        Float percent = 0f;

        for(TestResultEntity testResultEntity : testResultEntities){
            Integer correct = testResultEntity.getUserAnswers().stream().filter(
                    userAnswerEntity -> userAnswerEntity.getRightAnswer()
            ).toList().size();

            if(correct == 0){
                return 0f;
            }

            if(correct / (float) testResultEntity.getUserAnswers().size() > percent){
                percent = correct / (float) testResultEntity.getUserAnswers().size();
            }
        }

        return percent;
    }
}
