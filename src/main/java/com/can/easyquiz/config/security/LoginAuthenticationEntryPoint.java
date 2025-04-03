package com.can.easyquiz.config.security;

import com.can.easyquiz.enums.SystemCodeEnum;
import com.can.easyquiz.utils.RestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public final class LoginAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    public LoginAuthenticationEntryPoint() {
        super("/api/user/login");
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        RestUtil.response(response, SystemCodeEnum.UNAUTHORIZED);
    }
}