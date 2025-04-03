package com.can.easyquiz.controller.student;

import com.can.easyquiz.annotation.ApiController;
import com.can.easyquiz.annotation.RestResponse;
import com.can.easyquiz.domain.ExamPaperQuestionCustomerAnswer;
import com.can.easyquiz.domain.QuestionObject;
import com.can.easyquiz.domain.Subject;
import com.can.easyquiz.domain.TextContent;
import com.can.easyquiz.service.ExamPaperQuestionCustomerAnswerService;
import com.can.easyquiz.service.QuestionService;
import com.can.easyquiz.service.SubjectService;
import com.can.easyquiz.service.TextContentService;
import com.can.easyquiz.utils.DateTimeUtil;
import com.can.easyquiz.utils.HtmlUtil;
import com.can.easyquiz.utils.JsonUtil;
import com.can.easyquiz.utils.PageInfoHelper;
import com.can.easyquiz.viewmodel.admin.question.QuestionEditRequestVM;
import com.can.easyquiz.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.can.easyquiz.viewmodel.student.question.QuestionAnswerVM;
import com.can.easyquiz.viewmodel.student.question.QuestionPageStudentRequestVM;
import com.can.easyquiz.viewmodel.student.question.QuestionPageStudentResponseVM;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("StudentQuestionAnswerController")
@RequestMapping(value = "/api/student/question/answer")
public class QuestionAnswerController extends ApiController {

    private final ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    private final QuestionService questionService;
    private final TextContentService textContentService;
    private final SubjectService subjectService;

    @Autowired
    public QuestionAnswerController(ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService, QuestionService questionService, TextContentService textContentService, SubjectService subjectService) {
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
        this.questionService = questionService;
        this.textContentService = textContentService;
        this.subjectService = subjectService;
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<QuestionPageStudentResponseVM>> pageList(@RequestBody QuestionPageStudentRequestVM model) {
        model.setCreateUser(getCurrentUser().getId());
        PageInfo<ExamPaperQuestionCustomerAnswer> pageInfo = examPaperQuestionCustomerAnswerService.studentPage(model);
        PageInfo<QuestionPageStudentResponseVM> page = PageInfoHelper.copyMap(pageInfo, q -> {
            Subject subject = subjectService.selectById(q.getSubjectId());
            QuestionPageStudentResponseVM vm = modelMapper.map(q, QuestionPageStudentResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(q.getCreateTime()));
            TextContent textContent = textContentService.selectById(q.getQuestionTextContentId());
            QuestionObject questionObject = JsonUtil.toJsonObject(textContent.getContent(), QuestionObject.class);
            String clearHtml = HtmlUtil.clear(questionObject.getTitleContent());
            vm.setShortTitle(clearHtml);
            vm.setSubjectName(subject.getName());
            return vm;
        });
        return RestResponse.ok(page);
    }


    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<QuestionAnswerVM> select(@PathVariable Integer id) {
        QuestionAnswerVM vm = new QuestionAnswerVM();
        ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer = examPaperQuestionCustomerAnswerService.selectById(id);
        ExamPaperSubmitItemVM questionAnswerVM = examPaperQuestionCustomerAnswerService.examPaperQuestionCustomerAnswerToVM(examPaperQuestionCustomerAnswer);
        QuestionEditRequestVM questionVM = questionService.getQuestionEditRequestVM(examPaperQuestionCustomerAnswer.getQuestionId());
        vm.setQuestionVM(questionVM);
        vm.setQuestionAnswerVM(questionAnswerVM);
        return RestResponse.ok(vm);
    }

}