package com.can.easyquiz.config.security;

import com.can.easyquiz.config.property.System;
import com.can.easyquiz.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        List<String> securityIgnoreUrls = new System().getSecurityIgnoreUrls();
        String[] ignoreUrls = new String[securityIgnoreUrls.size()];
        http.headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.disable())
        ).authorizeHttpRequests(authorize -> authorize
                .requestMatchers(ignoreUrls).permitAll()
                .requestMatchers("/api/admin/**").hasRole(Role.ADMIN.getName())
                .requestMatchers("/api/student/**").hasRole(Role.STUDENT.getName())
                .anyRequest().permitAll()
        );

        return http.build();
    }
}
