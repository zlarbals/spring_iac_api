package com.example.spring_iac_api.config;

import com.example.spring_iac_api.interceptor.HeaderAuthKeyInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final HeaderAuthKeyInterceptor headerAuthKeyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headerAuthKeyInterceptor)
                .addPathPatterns("/**");
    }
}
