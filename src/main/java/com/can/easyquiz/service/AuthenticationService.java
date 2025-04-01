package com.can.easyquiz.service;

import com.can.easyquiz.domain.User;

public interface AuthenticationService {

    boolean authUser(String username, String password);

    boolean authUser(User user, String username, String password);

    String pwdEncode(String password);

    String pwdDecode(String endodePwd);
}
