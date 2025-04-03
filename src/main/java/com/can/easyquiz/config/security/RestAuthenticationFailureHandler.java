package com.can.easyquiz.config.security;

import com.can.easyquiz.enums.SystemCodeEnum;
import com.can.easyquiz.utils.RestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        RestUtil.response(response, SystemCodeEnum.AuthError.getCode(), exception.getMessage());
    }
}