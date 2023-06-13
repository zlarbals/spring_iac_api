package com.example.spring_iac_api.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class HeaderAuthKeyInterceptor implements HandlerInterceptor {

    @Value("${header.auth-key}")
    private String authKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestAuthKey = request.getHeader("AuthKey");

        return requestAuthKey.equals(authKey);
    }
}
