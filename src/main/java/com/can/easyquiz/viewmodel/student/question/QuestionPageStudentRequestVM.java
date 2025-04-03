package com.can.easyquiz.viewmodel.student.question;

import com.can.easyquiz.annotation.BasePage;

public class QuestionPageStudentRequestVM extends BasePage {
    private Integer createUser;

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
}
