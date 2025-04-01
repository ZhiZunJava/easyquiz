package com.can.easyquiz.viewmodel.admin.user;

import com.can.easyquiz.annotation.BasePage;

public class UserPageRequest  extends BasePage {

    private String userName;
    private Integer role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}