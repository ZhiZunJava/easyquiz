package com.can.easyquiz.repository;

import com.can.easyquiz.domain.ExamPaperAnswerUpdate;
import com.can.easyquiz.domain.ExamPaperQuestionCustomerAnswer;
import com.can.easyquiz.domain.KeyValue;
import com.can.easyquiz.viewmodel.student.question.QuestionPageStudentRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface ExamPaperQuestionCustomerAnswerMapper extends BasicMapper<ExamPaperQuestionCustomerAnswer> {
    List<ExamPaperQuestionCustomerAnswer> selectListByPaperAnswerId(Integer id);

    List<ExamPaperQuestionCustomerAnswer> studentPage(QuestionPageStudentRequestVM requestVM);

    int insertList(List<ExamPaperQuestionCustomerAnswer> list);

    Integer selectAllCount();

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    int updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates);
    
    /**
     * 按年级统计正确率
     * @return 包含年级名称、正确数量和总数量的列表
     */
    List<Map<String, Object>> selectCorrectRateByGradeLevel();
    
    /**
     * 按学科统计正确率
     * @return 包含学科名称、正确数量和总数量的列表
     */
    List<Map<String, Object>> selectCorrectRateBySubject();
}
