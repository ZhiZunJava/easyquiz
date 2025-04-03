package com.can.easyquiz.repository;

import com.can.easyquiz.domain.ExamPaperAnswer;
import com.can.easyquiz.domain.KeyValue;
import com.can.easyquiz.viewmodel.admin.paper.ExamPaperAnswerPageRequestVM;
import com.can.easyquiz.viewmodel.student.exampaper.ExamPaperAnswerPageVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ExamPaperAnswerMapper extends BasicMapper<ExamPaperAnswer> {

    List<ExamPaperAnswer> studentPage(ExamPaperAnswerPageVM requestVM);

    Integer selectAllCount();

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    ExamPaperAnswer getByPidUid(@Param("pid") Integer paperId, @Param("uid") Integer uid);

    List<ExamPaperAnswer> adminPage(ExamPaperAnswerPageRequestVM requestVM);

}
