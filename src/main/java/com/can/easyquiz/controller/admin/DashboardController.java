package com.can.easyquiz.controller.admin;

import com.can.easyquiz.annotation.ApiController;
import com.can.easyquiz.annotation.RestResponse;
import com.can.easyquiz.service.*;
import com.can.easyquiz.viewmodel.admin.dashboard.IndexVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController("AdminDashboardController")
@RequestMapping(value = "/api/admin/dashboard")
public class DashboardController extends ApiController {

    private final ExamPaperService examPaperService;
    private final QuestionService questionService;
    private final ExamPaperAnswerService examPaperAnswerService;
    private final ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    private final UserEventLogService userEventLogService;

    @Autowired
    public DashboardController(ExamPaperService examPaperService, QuestionService questionService, ExamPaperAnswerService examPaperAnswerService, ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService, UserEventLogService userEventLogService) {
        this.examPaperService = examPaperService;
        this.questionService = questionService;
        this.examPaperAnswerService = examPaperAnswerService;
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
        this.userEventLogService = userEventLogService;
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public RestResponse<IndexVM> Index() {
        IndexVM vm = new IndexVM();

        // 基础统计信息
        Integer examPaperCount = examPaperService.selectAllCount();
        Integer questionCount = questionService.selectAllCount();
        Integer doExamPaperCount = examPaperAnswerService.selectAllCount();
        Integer doQuestionCount = examPaperQuestionCustomerAnswerService.selectAllCount();

        vm.setExamPaperCount(examPaperCount);
        vm.setQuestionCount(questionCount);
        vm.setDoExamPaperCount(doExamPaperCount);
        vm.setDoQuestionCount(doQuestionCount);
        
        // 按年级统计题目数量
        Map<String, Integer> questionCountByGradeLevel = questionService.selectCountByGradeLevel();
        vm.setQuestionCountByGradeLevel(questionCountByGradeLevel);
        
        // 按题目类型统计数量
        Map<String, Integer> questionCountByType = questionService.selectCountByType();
        vm.setQuestionCountByType(questionCountByType);
        
        // 按难度统计题目数量
        Map<String, Integer> questionCountByDifficulty = questionService.selectCountByDifficulty();
        vm.setQuestionCountByDifficulty(questionCountByDifficulty);
        
        // 按年级统计正确率
        Map<String, Double> correctRateByGradeLevel = examPaperQuestionCustomerAnswerService.selectCorrectRateByGradeLevel();
        vm.setCorrectRateByGradeLevel(correctRateByGradeLevel);
        
        // 按难度统计正确率
        Map<String, Double> correctRateByDifficulty = examPaperQuestionCustomerAnswerService.selectCorrectRateByDifficulty();
        vm.setCorrectRateByDifficulty(correctRateByDifficulty);
        
        // 获取热门题目TOP 5
        List<Map<String, Object>> hotQuestions = questionService.selectHotQuestions(5);
        vm.setHotQuestions(hotQuestions);

        return RestResponse.ok(vm);
    }
}