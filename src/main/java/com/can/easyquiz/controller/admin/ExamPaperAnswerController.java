package com.can.easyquiz.controller.admin;

import com.can.easyquiz.annotation.ApiController;
import com.can.easyquiz.annotation.RestResponse;
import com.can.easyquiz.domain.ExamPaperAnswer;
import com.can.easyquiz.domain.Subject;
import com.can.easyquiz.domain.User;
import com.can.easyquiz.domain.UserEventLog;
import com.can.easyquiz.enums.ExamPaperAnswerStatusEnum;
import com.can.easyquiz.event.UserEvent;
import com.can.easyquiz.service.ExamPaperAnswerService;
import com.can.easyquiz.service.ExamPaperService;
import com.can.easyquiz.service.SubjectService;
import com.can.easyquiz.service.UserService;
import com.can.easyquiz.utils.DateTimeUtil;
import com.can.easyquiz.utils.ExamUtil;
import com.can.easyquiz.utils.PageInfoHelper;
import com.can.easyquiz.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.can.easyquiz.viewmodel.admin.paper.ExamPaperAnswerPageRequestVM;
import com.can.easyquiz.viewmodel.student.exam.ExamPaperReadVM;
import com.can.easyquiz.viewmodel.student.exam.ExamPaperSubmitVM;
import com.can.easyquiz.viewmodel.student.exampaper.ExamPaperAnswerPageResponseVM;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController("AdminExamPaperAnswerController")
@RequestMapping(value = "/api/admin/examPaperAnswer")
public class ExamPaperAnswerController extends ApiController {

    private final ExamPaperAnswerService examPaperAnswerService;
    private final SubjectService subjectService;
    private final UserService userService;
    private final ExamPaperService examPaperService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ExamPaperAnswerController(ExamPaperAnswerService examPaperAnswerService, SubjectService subjectService, 
                                    UserService userService, ExamPaperService examPaperService,
                                    ApplicationEventPublisher eventPublisher) {
        this.examPaperAnswerService = examPaperAnswerService;
        this.subjectService = subjectService;
        this.userService = userService;
        this.examPaperService = examPaperService;
        this.eventPublisher = eventPublisher;
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperAnswerPageResponseVM>> pageList(@RequestBody ExamPaperAnswerPageRequestVM model) {
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
    
    @RequestMapping(value = "/waitList", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperAnswerPageResponseVM>> waitList(@RequestBody ExamPaperAnswerPageRequestVM model) {
        model.setStatus(ExamPaperAnswerStatusEnum.WaitJudge.getCode());
        return pageList(model);
    }
    
    @RequestMapping(value = "/completeList", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperAnswerPageResponseVM>> completeList(@RequestBody ExamPaperAnswerPageRequestVM model) {
        model.setStatus(ExamPaperAnswerStatusEnum.Complete.getCode());
        return pageList(model);
    }
    
    @RequestMapping(value = "/read/{id}", method = RequestMethod.POST)
    public RestResponse<ExamPaperReadVM> read(@PathVariable Integer id) {
        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.selectById(id);
        ExamPaperReadVM vm = new ExamPaperReadVM();
        ExamPaperEditRequestVM paper = examPaperService.examPaperToVM(examPaperAnswer.getExamPaperId());
        ExamPaperSubmitVM answer = examPaperAnswerService.examPaperAnswerToVM(examPaperAnswer.getId());
        vm.setPaper(paper);
        vm.setAnswer(answer);
        return RestResponse.ok(vm);
    }
    
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse edit(@RequestBody @Valid ExamPaperSubmitVM examPaperSubmitVM) {
        boolean notJudge = examPaperSubmitVM.getAnswerItems().stream().anyMatch(i -> i.getDoRight() == null && i.getScore() == null);
        if (notJudge) {
            return RestResponse.fail(2, "有未批改题目");
        }

        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.selectById(examPaperSubmitVM.getId());
        ExamPaperAnswerStatusEnum examPaperAnswerStatusEnum = ExamPaperAnswerStatusEnum.fromCode(examPaperAnswer.getStatus());
        if (examPaperAnswerStatusEnum == ExamPaperAnswerStatusEnum.Complete) {
            return RestResponse.fail(3, "试卷已完成批改");
        }
        String score = examPaperAnswerService.judge(examPaperSubmitVM);
        User user = getCurrentUser();
        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
        String content = user.getUserName() + " 批改试卷：" + examPaperAnswer.getPaperName() + " 得分：" + score;
        userEventLog.setContent(content);
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        return RestResponse.ok(score);
    }
}