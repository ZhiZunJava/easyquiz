package com.can.easyquiz.repository;

import com.can.easyquiz.domain.KeyValue;
import com.can.easyquiz.domain.Question;
import com.can.easyquiz.viewmodel.admin.question.QuestionPageRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    
    /**
     * 按年级统计题目数量
     * @return 年级名称和数量的键值对列表
     */
    List<KeyValue> selectCountByGradeLevel();
    
    /**
     * 按题目类型统计数量
     * @return 题目类型和数量的键值对列表
     */
    List<KeyValue> selectCountByType();
    
    /**
     * 按难度统计题目数量
     * @return 难度等级和数量的键值对列表
     */
    List<KeyValue> selectCountByDifficulty();
    
    /**
     * 获取热门题目（被答题次数最多的题目）
     * @param limit 数量限制
     * @return 热门题目列表
     */
    List<Map<String, Object>> selectHotQuestions(@Param("limit") int limit);
}