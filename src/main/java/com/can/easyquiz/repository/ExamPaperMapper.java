package com.can.easyquiz.repository;

import com.can.easyquiz.domain.ExamPaper;
import com.can.easyquiz.domain.KeyValue;
import com.can.easyquiz.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.can.easyquiz.viewmodel.student.dashboard.PaperFilter;
import com.can.easyquiz.viewmodel.student.dashboard.PaperInfo;
import com.can.easyquiz.viewmodel.student.exam.ExamPaperPageVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ExamPaperMapper extends BasicMapper<ExamPaper> {
    List<ExamPaper> page(ExamPaperPageRequestVM requestVM);

    List<ExamPaper> taskExamPage(ExamPaperPageRequestVM requestVM);

    List<ExamPaper> studentPage(ExamPaperPageVM requestVM);

    List<PaperInfo> indexPaper(PaperFilter paperFilter);

    Integer selectAllCount();

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    int updateTaskPaper(@Param("taskId") Integer taskId, @Param("paperIds") List<Integer> paperIds);

    int clearTaskPaper(@Param("paperIds") List<Integer> paperIds);
}
