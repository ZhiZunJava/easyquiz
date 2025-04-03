package com.can.easyquiz.controller.student;

import com.can.easyquiz.annotation.ApiController;
import com.can.easyquiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("StudentQuestionController")
@RequestMapping(value = "/api/student/question")
public class QuestionController extends ApiController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
}