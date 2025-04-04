package com.can.easyquiz.service;

import com.can.easyquiz.domain.ExamPaperAnswerUpdate;
import com.can.easyquiz.domain.ExamPaperQuestionCustomerAnswer;
import com.can.easyquiz.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.can.easyquiz.viewmodel.student.question.QuestionPageStudentRequestVM;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

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
    
    /**
     * 按年级统计题目正确率
     * @return 年级-正确率映射
     */
    Map<String, Double> selectCorrectRateByGradeLevel();
    
    /**
     * 按学科统计题目正确率
     * @return 学科-正确率映射
     */
    Map<String, Double> selectCorrectRateByDifficulty();
}
