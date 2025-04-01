package com.can.easyquiz.config;

import com.can.easyquiz.config.property.System;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    private final System systemConfig;

    /**
     * Instantiates a new Web mvc configuration.
     *
     * @param systemConfig            the system config
     */
    @Autowired
    public WebMvcConfiguration(System systemConfig) {
        this.systemConfig = systemConfig;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/student/index.html");
        registry.addRedirectViewController("/student", "/student/index.html");
        registry.addRedirectViewController("/admin", "/admin/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(31556926);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
        super.addCorsMappings(registry);
    }
}
