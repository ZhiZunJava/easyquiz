package com.can.easyquiz.viewmodel.student.exampaper;

import com.can.easyquiz.annotation.BasePage;

public class ExamPaperAnswerPageVM extends BasePage {
    private Integer subjectId;

    private Integer createUser;

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
}
