package com.can.easyquiz.viewmodel.student.exam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ExamPaperSubmitVM {
    @NotNull
    private Integer id;

    @NotNull
    private Integer doTime;
    private String doTimeStr;

    private String score;

    @NotNull
    @Valid
    private List<ExamPaperSubmitItemVM> answerItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoTime() {
        return doTime;
    }

    public void setDoTime(Integer doTime) {
        this.doTime = doTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<ExamPaperSubmitItemVM> getAnswerItems() {
        return answerItems;
    }

    public void setAnswerItems(List<ExamPaperSubmitItemVM> answerItems) {
        this.answerItems = answerItems;
    }

    public String getDoTimeStr() {
        return doTimeStr;
    }

    public void setDoTimeStr(String doTimeStr) {
        this.doTimeStr = doTimeStr;
    }
}
