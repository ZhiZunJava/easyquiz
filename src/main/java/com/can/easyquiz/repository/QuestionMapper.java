package com.can.easyquiz.repository;

import com.can.easyquiz.domain.KeyValue;
import com.can.easyquiz.domain.Question;
import com.can.easyquiz.viewmodel.admin.question.QuestionPageRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface QuestionMapper extends BasicMapper<Question> {

    List<Question> page(QuestionPageRequestVM requestVM);

    List<Question> selectByIds(@Param("ids") List<Integer> ids);

    Integer selectAllCount();

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 根据题目类型和难度随机选择题目
     *
     * @param subjectId 科目ID
     * @param questionType 题目类型
     * @param difficulty 难度等级
     * @param count 需要的题目数量
     * @return 题目列表
     */
    List<Question> selectRandomQuestionsByTypeAndDifficulty(
        @Param("subjectId") Integer subjectId,
        @Param("questionType") Integer questionType,
        @Param("difficulty") Integer difficulty,
        @Param("count") Integer count
    );
}