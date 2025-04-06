package com.can.easyquiz.service;

import com.can.easyquiz.domain.ExamPaper;
import com.can.easyquiz.domain.User;
import com.can.easyquiz.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.can.easyquiz.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.can.easyquiz.viewmodel.student.dashboard.PaperFilter;
import com.can.easyquiz.viewmodel.student.dashboard.PaperInfo;
import com.can.easyquiz.viewmodel.student.exam.ExamPaperPageVM;
import com.can.easyquiz.viewmodel.paper.ExamPaperGenerateVM;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ExamPaperService extends BasicService<ExamPaper> {

    PageInfo<ExamPaper> page(ExamPaperPageRequestVM requestVM);

    PageInfo<ExamPaper> studentPage(ExamPaperPageVM requestVM);

    ExamPaper savePaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, User user);

    ExamPaperEditRequestVM examPaperToVM(Integer id);

    List<PaperInfo> indexPaper(PaperFilter paperFilter);

    Integer selectAllCount();

    /**
     * 生成智能训练试卷
     *
     * @param model 试卷生成参数
     * @param user 当前用户
     * @return ExamPaper
     */
    ExamPaper generateSmartPaper(ExamPaperGenerateVM model, User user);
}