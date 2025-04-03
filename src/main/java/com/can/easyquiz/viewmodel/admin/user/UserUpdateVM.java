package com.can.easyquiz.viewmodel.admin.user;

import jakarta.validation.constraints.NotBlank;

public class UserUpdateVM {
    @NotBlank
    private String realName;

    @NotBlank
    private String phone;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
