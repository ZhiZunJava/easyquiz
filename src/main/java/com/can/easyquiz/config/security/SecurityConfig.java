package com.can.easyquiz.config.security;

import com.can.easyquiz.config.property.Cookie;
import com.can.easyquiz.config.property.System;
import com.can.easyquiz.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final System systemConfig;
    private final LoginAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAuthenticationProvider restAuthenticationProvider;
    private final RestDetailsServiceImpl formDetailsService;
    private final RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
    private final RestAuthenticationFailureHandler restAuthenticationFailureHandler;
    private final RestLogoutSuccessHandler restLogoutSuccessHandler;
    private final RestAccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    public SecurityConfig(System systemConfig,
                              LoginAuthenticationEntryPoint restAuthenticationEntryPoint,
                              RestAuthenticationProvider restAuthenticationProvider,
                              RestDetailsServiceImpl formDetailsService,
                              RestAuthenticationSuccessHandler restAuthenticationSuccessHandler,
                              RestAuthenticationFailureHandler restAuthenticationFailureHandler,
                              RestLogoutSuccessHandler restLogoutSuccessHandler,
                              RestAccessDeniedHandler restAccessDeniedHandler) {
        this.systemConfig = systemConfig;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.restAuthenticationProvider = restAuthenticationProvider;
        this.formDetailsService = formDetailsService;
        this.restAuthenticationSuccessHandler = restAuthenticationSuccessHandler;
        this.restAuthenticationFailureHandler = restAuthenticationFailureHandler;
        this.restLogoutSuccessHandler = restLogoutSuccessHandler;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        // 禁用 frameOptions
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        List<String> securityIgnoreUrls = systemConfig.getSecurityIgnoreUrls();
        String[] ignores = (securityIgnoreUrls != null) ? securityIgnoreUrls.toArray(new String[0]) : new String[0];

        http
                // 替换默认的 UsernamePasswordAuthenticationFilter
                .addFilterAt(authenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                // 配置异常处理
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(restAccessDeniedHandler)
                )
                // 配置认证提供者
                .authenticationProvider(restAuthenticationProvider)
                // 配置请求授权
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(ignores).permitAll()
                        .requestMatchers("/api/admin/**").hasRole(Role.ADMIN.getName())
                        .requestMatchers("/api/student/**").hasRole(Role.STUDENT.getName())
                        .anyRequest().permitAll()
                )
                // 配置表单登录
                .formLogin(formLogin -> formLogin
                        .successHandler(restAuthenticationSuccessHandler)
                        .failureHandler(restAuthenticationFailureHandler)
                )
                // 配置登出
                .logout(logout -> logout
                        .logoutUrl("/api/user/logout")
                        .logoutSuccessHandler(restLogoutSuccessHandler)
                        .invalidateHttpSession(true)
                )
                // 配置记住我功能
                .rememberMe(rememberMe -> rememberMe
                        .key(Cookie.getName())
                        .tokenValiditySeconds(Cookie.getInterval())
                        .userDetailsService(formDetailsService)
                )
                // 禁用 CSRF
                .csrf(csrf -> csrf.disable())
                // 配置 CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(false); // 禁用凭据
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

    @Bean
    public RestLoginAuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager) {
        RestLoginAuthenticationFilter authenticationFilter = new RestLoginAuthenticationFilter();
        authenticationFilter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
        authenticationFilter.setAuthenticationManager(authenticationManager);
        authenticationFilter.setUserDetailsService(formDetailsService);
        return authenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}