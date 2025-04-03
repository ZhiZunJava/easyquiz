package com.can.easyquiz.viewmodel.admin.paper;

import com.can.easyquiz.annotation.BasePage;

public class ExamPaperAnswerPageRequestVM extends BasePage {
    private Integer subjectId;

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }
}