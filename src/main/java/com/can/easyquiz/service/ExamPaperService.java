package com.can.easyquiz.service;

import com.can.easyquiz.domain.ExamPaper;
import com.can.easyquiz.domain.User;
import com.can.easyquiz.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.can.easyquiz.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.can.easyquiz.viewmodel.student.dashboard.PaperFilter;
import com.can.easyquiz.viewmodel.student.dashboard.PaperInfo;
import com.can.easyquiz.viewmodel.student.exam.ExamPaperPageVM;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ExamPaperService extends BasicService<ExamPaper> {

    PageInfo<ExamPaper> page(ExamPaperPageRequestVM requestVM);

    PageInfo<ExamPaper> taskExamPage(ExamPaperPageRequestVM requestVM);

    PageInfo<ExamPaper> studentPage(ExamPaperPageVM requestVM);

    ExamPaper savePaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, User user);

    ExamPaperEditRequestVM examPaperToVM(Integer id);

    List<PaperInfo> indexPaper(PaperFilter paperFilter);

    Integer selectAllCount();

    List<Integer> selectMothCount();
}