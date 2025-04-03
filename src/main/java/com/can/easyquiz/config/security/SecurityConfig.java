package com.can.easyquiz.config.security;

import com.can.easyquiz.config.property.CookieConfig;
import com.can.easyquiz.config.property.SystemConfig;
import com.can.easyquiz.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SystemConfig systemConfig;
    private final LoginAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAuthenticationProvider restAuthenticationProvider;
    private final RestDetailsServiceImpl formDetailsService;
    private final RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
    private final RestAuthenticationFailureHandler restAuthenticationFailureHandler;
    private final RestLogoutSuccessHandler restLogoutSuccessHandler;
    private final RestAccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    public SecurityConfig(SystemConfig systemConfig,
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
        List<String> securityIgnoreUrls = systemConfig.getSecurityIgnoreUrls();
        String[] ignores = (securityIgnoreUrls != null) ? securityIgnoreUrls.toArray(new String[0]) : new String[0];

        http
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                // 添加session管理
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
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
                        .requestMatchers("/api/admin/**").hasRole(RoleEnum.ADMIN.getName())
                        .requestMatchers("/api/student/**").hasRole(RoleEnum.STUDENT.getName())
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
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                )
                // 配置记住我功能
                .rememberMe(rememberMe -> rememberMe
                        .key(CookieConfig.getName())
                        .tokenValiditySeconds(CookieConfig.getInterval())
                        .userDetailsService(formDetailsService)
                )
                .csrf(AbstractHttpConfigurer::disable)
                // 配置 CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(List.of("*")); // 使用模式匹配
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

    @Bean
    public RestLoginAuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager) {
        RestLoginAuthenticationFilter authenticationFilter = new RestLoginAuthenticationFilter();
        authenticationFilter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
        authenticationFilter.setAuthenticationManager(authenticationManager);
        authenticationFilter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        authenticationFilter.setUserDetailsService(formDetailsService);
        return authenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}