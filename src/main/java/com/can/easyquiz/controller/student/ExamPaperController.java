package com.can.easyquiz.controller.student;

import com.can.easyquiz.annotation.ApiController;
import com.can.easyquiz.annotation.RestResponse;
import com.can.easyquiz.domain.ExamPaper;
import com.can.easyquiz.service.ExamPaperAnswerService;
import com.can.easyquiz.service.ExamPaperService;
import com.can.easyquiz.utils.DateTimeUtil;
import com.can.easyquiz.utils.PageInfoHelper;
import com.can.easyquiz.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.can.easyquiz.viewmodel.student.exam.ExamPaperPageResponseVM;
import com.can.easyquiz.viewmodel.student.exam.ExamPaperPageVM;
import com.can.easyquiz.viewmodel.paper.ExamPaperGenerateVM;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController("StudentExamPaperController")
@RequestMapping(value = "/api/student/exam/paper")
public class ExamPaperController extends ApiController {

    private final ExamPaperService examPaperService;
    private final ExamPaperAnswerService examPaperAnswerService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ExamPaperController(ExamPaperService examPaperService, ExamPaperAnswerService examPaperAnswerService, ApplicationEventPublisher eventPublisher) {
        this.examPaperService = examPaperService;
        this.examPaperAnswerService = examPaperAnswerService;
        this.eventPublisher = eventPublisher;
    }


    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> select(@PathVariable Integer id) {
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(id);
        return RestResponse.ok(vm);
    }


    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperPageResponseVM>> pageList(@RequestBody @Valid ExamPaperPageVM model) {
        PageInfo<ExamPaper> pageInfo = examPaperService.studentPage(model);
        PageInfo<ExamPaperPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamPaperPageResponseVM vm = modelMapper.map(e, ExamPaperPageResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> generatePaper(@RequestBody @Valid ExamPaperGenerateVM model) {
        ExamPaper examPaper = examPaperService.generateSmartPaper(model, getCurrentUser());
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(examPaper.getId());
        return RestResponse.ok(vm);
    }
}