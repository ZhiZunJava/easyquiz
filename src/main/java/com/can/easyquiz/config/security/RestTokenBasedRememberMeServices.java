package com.can.easyquiz.config.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

public class RestTokenBasedRememberMeServices extends TokenBasedRememberMeServices {
    /**
     * Instantiates a new Rest token based remember me services.
     *
     * @param key                the key
     * @param userDetailsService the user details service
     */
    public RestTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        return (boolean) request.getAttribute(DEFAULT_PARAMETER);
    }

}