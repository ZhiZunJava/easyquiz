package com.can.easyquiz.viewmodel.admin.paper;

import com.can.easyquiz.annotation.BasePage;

public class ExamPaperAnswerPageRequestVM extends BasePage {
    private Integer subjectId;
    private Integer status;

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
}