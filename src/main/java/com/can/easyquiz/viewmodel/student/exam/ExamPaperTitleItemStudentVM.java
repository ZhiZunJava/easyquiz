package com.can.easyquiz.viewmodel.student.exam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ExamPaperTitleItemStudentVM {
    @NotBlank(message = "标题内容不能为空")
    private String name;

    @Size(min = 1,message = "请添加题目")
    @Valid
    private List<QuestionStudentVM> questionItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QuestionStudentVM> getQuestionItems() {
        return questionItems;
    }

    public void setQuestionItems(List<QuestionStudentVM> questionItems) {
        this.questionItems = questionItems;
    }
} 