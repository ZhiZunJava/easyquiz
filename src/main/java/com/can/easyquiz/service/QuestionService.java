package com.can.easyquiz.service;

import com.can.easyquiz.domain.Question;
import com.can.easyquiz.viewmodel.admin.question.QuestionEditRequestVM;
import com.can.easyquiz.viewmodel.admin.question.QuestionPageRequestVM;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface QuestionService extends BasicService<Question> {

    PageInfo<Question> page(QuestionPageRequestVM requestVM);

    Question insertFullQuestion(QuestionEditRequestVM model, Integer userId);

    Question updateFullQuestion(QuestionEditRequestVM model);

    QuestionEditRequestVM getQuestionEditRequestVM(Integer questionId);

    QuestionEditRequestVM getQuestionEditRequestVM(Question question);

    Integer selectAllCount();

    List<Integer> selectMothCount();
}