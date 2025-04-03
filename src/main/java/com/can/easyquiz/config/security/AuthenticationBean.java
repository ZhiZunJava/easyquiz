package com.can.easyquiz.config.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationBean {
    private String userName;
    private String password;
    private boolean remember;
}
