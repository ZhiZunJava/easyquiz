package com.can.easyquiz.service.impl;

import com.can.easyquiz.config.property.System;
import com.can.easyquiz.domain.User;
import com.can.easyquiz.service.AuthenticationService;
import com.can.easyquiz.service.UserService;
import com.can.easyquiz.utils.RsaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final System systemConfig;

    @Autowired
    public AuthenticationServiceImpl(UserService userService, System systemConfig) {
        this.userService = userService;
        this.systemConfig = systemConfig;
    }

    @Override
    public boolean authUser(String username, String password) {
        User user = userService.getUserByUserName(username);
        return authUser(user, username, password);
    }

    @Override
    public boolean authUser(User user, String username, String password) {
        if (user == null) {
            return false;
        }
        String encodePwd = user.getPassword();
        if (null == encodePwd || encodePwd.length() == 0) {
            return false;
        }
        String pwd = pwdDecode(encodePwd);
        return pwd.equals(password);
    }

    @Override
    public String pwdEncode(String password) {
        return RsaUtil.rsaEncode(systemConfig.getPwdKey().getPublicKey(), password);
    }

    @Override
    public String pwdDecode(String encodePwd) {
        return RsaUtil.rsaDecode(systemConfig.getPwdKey().getPrivateKey(), encodePwd);
    }
}
