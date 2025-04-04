package com.can.easyquiz.service;

import com.can.easyquiz.domain.Question;
import com.can.easyquiz.viewmodel.admin.question.QuestionEditRequestVM;
import com.can.easyquiz.viewmodel.admin.question.QuestionPageRequestVM;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface QuestionService extends BasicService<Question> {

    PageInfo<Question> page(QuestionPageRequestVM requestVM);

    Question insertFullQuestion(QuestionEditRequestVM model, Integer userId);

    Question updateFullQuestion(QuestionEditRequestVM model);

    QuestionEditRequestVM getQuestionEditRequestVM(Integer questionId);

    QuestionEditRequestVM getQuestionEditRequestVM(Question question);

    Integer selectAllCount();

    List<Integer> selectMothCount();
    
    /**
     * 按年级统计题目数量
     * @return 年级-数量映射
     */
    Map<String, Integer> selectCountByGradeLevel();
    
    /**
     * 按学科统计题目数量
     * @return 学科-数量映射
     */
    Map<String, Integer> selectCountBySubject();
    
    /**
     * 按题目类型统计数量
     * @return 题型-数量映射
     */
    Map<String, Integer> selectCountByType();
    
    /**
     * 按难度统计题目数量
     * @return 难度-数量映射
     */
    Map<String, Integer> selectCountByDifficulty();
    
    /**
     * 获取热门题目（被答题次数最多的题目）
     * @param limit 数量限制
     * @return 热门题目列表
     */
    List<Map<String, Object>> selectHotQuestions(int limit);
}