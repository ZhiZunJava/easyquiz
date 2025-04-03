package com.can.easyquiz.viewmodel.admin.message;

import com.can.easyquiz.annotation.BasePage;

public class MessagePageRequestVM extends BasePage {
    private String sendUserName;

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }
}