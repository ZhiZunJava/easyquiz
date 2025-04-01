package com.can.easyquiz.config.security;


import com.can.easyquiz.config.property.Cookie;
import com.can.easyquiz.utils.JsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.io.InputStream;

public class RestLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(RestLoginAuthenticationFilter.class);

    /**
     * Instantiates a new Rest login authentication filter.
     */
    public RestLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/user/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try (InputStream is = request.getInputStream()) {
            AuthenticationBean authenticationBean = JsonUtil.toJsonObject(is, AuthenticationBean.class);
            request.setAttribute(TokenBasedRememberMeServices.DEFAULT_PARAMETER, authenticationBean.isRemember());
            authRequest = new UsernamePasswordAuthenticationToken(authenticationBean.getUserName(), authenticationBean.getPassword());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            authRequest = new UsernamePasswordAuthenticationToken("", "");
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);

    }

    /**
     * Sets user details service.
     *
     * @param userDetailsService the user details service
     */
    void setUserDetailsService(UserDetailsService userDetailsService) {
        RestTokenBasedRememberMeServices tokenBasedRememberMeServices = new RestTokenBasedRememberMeServices(Cookie.getName(), userDetailsService);
        tokenBasedRememberMeServices.setTokenValiditySeconds(Cookie.getInterval());
        setRememberMeServices(tokenBasedRememberMeServices);
    }

    private void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}