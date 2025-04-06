package com.can.easyquiz.controller.student;

import com.can.easyquiz.annotation.ApiController;
import com.can.easyquiz.annotation.RestResponse;
import com.can.easyquiz.domain.User;
import com.can.easyquiz.enums.ExamPaperTypeEnum;
import com.can.easyquiz.service.ExamPaperService;
import com.can.easyquiz.service.QuestionService;
import com.can.easyquiz.service.TextContentService;
import com.can.easyquiz.service.UserService;
import com.can.easyquiz.utils.DateTimeUtil;
import com.can.easyquiz.viewmodel.student.dashboard.IndexVM;
import com.can.easyquiz.viewmodel.student.dashboard.PaperFilter;
import com.can.easyquiz.viewmodel.student.dashboard.PaperInfo;
import com.can.easyquiz.viewmodel.student.dashboard.PaperInfoVM;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController("StudentDashboardController")
@RequestMapping(value = "/api/student/dashboard")
public class DashboardController extends ApiController {

    private final UserService userService;
    private final ExamPaperService examPaperService;
    private final QuestionService questionService;
    private final TextContentService textContentService;

    @Autowired
    public DashboardController(UserService userService, ExamPaperService examPaperService, QuestionService questionService, TextContentService textContentService) {
        this.userService = userService;
        this.examPaperService = examPaperService;
        this.questionService = questionService;
        this.textContentService = textContentService;
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public RestResponse<IndexVM> index() {
        IndexVM indexVM = new IndexVM();
        User user = getCurrentUser();


        PaperFilter fixedPaperFilter = new PaperFilter();
        fixedPaperFilter.setGradeLevel(user.getUserLevel());
        fixedPaperFilter.setExamPaperType(ExamPaperTypeEnum.Fixed.getCode());
        indexVM.setFixedPaper(examPaperService.indexPaper(fixedPaperFilter));

        PaperFilter timeLimitPaperFilter = new PaperFilter();
        timeLimitPaperFilter.setDateTime(new Date());
        timeLimitPaperFilter.setGradeLevel(user.getUserLevel());
        timeLimitPaperFilter.setExamPaperType(ExamPaperTypeEnum.TimeLimit.getCode());

        List<PaperInfo> limitPaper = examPaperService.indexPaper(timeLimitPaperFilter);
        List<PaperInfoVM> paperInfoVMS = limitPaper.stream().map(d -> {
            PaperInfoVM vm = modelMapper.map(d, PaperInfoVM.class);
            vm.setStartTime(DateTimeUtil.dateFormat(d.getLimitStartTime()));
            vm.setEndTime(DateTimeUtil.dateFormat(d.getLimitEndTime()));
            return vm;
        }).collect(Collectors.toList());
        indexVM.setTimeLimitPaper(paperInfoVMS);
        return RestResponse.ok(indexVM);
    }
}