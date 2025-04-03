package com.can.easyquiz.controller.admin;

import com.can.easyquiz.annotation.ApiController;
import com.can.easyquiz.annotation.RestResponse;
import com.can.easyquiz.domain.ExamPaperAnswer;
import com.can.easyquiz.domain.Subject;
import com.can.easyquiz.domain.User;
import com.can.easyquiz.service.ExamPaperAnswerService;
import com.can.easyquiz.service.SubjectService;
import com.can.easyquiz.service.UserService;
import com.can.easyquiz.utils.DateTimeUtil;
import com.can.easyquiz.utils.ExamUtil;
import com.can.easyquiz.utils.PageInfoHelper;
import com.can.easyquiz.viewmodel.admin.paper.ExamPaperAnswerPageRequestVM;
import com.can.easyquiz.viewmodel.student.exampaper.ExamPaperAnswerPageResponseVM;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("AdminExamPaperAnswerController")
@RequestMapping(value = "/api/admin/examPaperAnswer")
public class ExamPaperAnswerController extends ApiController {

    private final ExamPaperAnswerService examPaperAnswerService;
    private final SubjectService subjectService;
    private final UserService userService;

    @Autowired
    public ExamPaperAnswerController(ExamPaperAnswerService examPaperAnswerService, SubjectService subjectService, UserService userService) {
        this.examPaperAnswerService = examPaperAnswerService;
        this.subjectService = subjectService;
        this.userService = userService;
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperAnswerPageResponseVM>> pageJudgeList(@RequestBody ExamPaperAnswerPageRequestVM model) {
        PageInfo<ExamPaperAnswer> pageInfo = examPaperAnswerService.adminPage(model);
        PageInfo<ExamPaperAnswerPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamPaperAnswerPageResponseVM vm = modelMapper.map(e, ExamPaperAnswerPageResponseVM.class);
            Subject subject = subjectService.selectById(vm.getSubjectId());
            vm.setDoTime(ExamUtil.secondToVM(e.getDoTime()));
            vm.setSystemScore(ExamUtil.scoreToVM(e.getSystemScore()));
            vm.setUserScore(ExamUtil.scoreToVM(e.getUserScore()));
            vm.setPaperScore(ExamUtil.scoreToVM(e.getPaperScore()));
            vm.setSubjectName(subject.getName());
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            User user = userService.selectById(e.getCreateUser());
            vm.setUserName(user.getUserName());
            return vm;
        });
        return RestResponse.ok(page);
    }

}