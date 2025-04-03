package com.can.easyquiz.viewmodel.student.user;

import com.can.easyquiz.annotation.BasePage;

public class MessageRequestVM extends BasePage {
    private Integer receiveUserId;

    public Integer getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Integer receiveUserId) {
        this.receiveUserId = receiveUserId;
    }
}
