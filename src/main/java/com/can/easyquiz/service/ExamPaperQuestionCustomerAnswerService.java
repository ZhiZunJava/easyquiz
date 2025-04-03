package com.can.easyquiz.service;

import com.can.easyquiz.domain.ExamPaperAnswerUpdate;
import com.can.easyquiz.domain.ExamPaperQuestionCustomerAnswer;
import com.can.easyquiz.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.can.easyquiz.viewmodel.student.question.QuestionPageStudentRequestVM;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ExamPaperQuestionCustomerAnswerService extends BasicService<ExamPaperQuestionCustomerAnswer>{
    PageInfo<ExamPaperQuestionCustomerAnswer> studentPage(QuestionPageStudentRequestVM requestVM);

    List<ExamPaperQuestionCustomerAnswer> selectListByPaperAnswerId(Integer id);

    /**
     * 试卷提交答案入库
     *
     * @param examPaperQuestionCustomerAnswers List<ExamPaperQuestionCustomerAnswer>
     */
    void insertList(List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers);

    /**
     * 试卷问题答题信息转成ViewModel 传给前台
     *
     * @param qa ExamPaperQuestionCustomerAnswer
     * @return ExamPaperSubmitItemVM
     */
    ExamPaperSubmitItemVM examPaperQuestionCustomerAnswerToVM(ExamPaperQuestionCustomerAnswer qa);


    Integer selectAllCount();

    List<Integer> selectMothCount();

    int updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates);
}
