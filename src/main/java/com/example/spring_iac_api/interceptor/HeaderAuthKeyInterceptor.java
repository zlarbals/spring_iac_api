package com.example.spring_iac_api.interceptor;

import com.example.spring_iac_api.exception.AuthKeyInvalidException;
import com.example.spring_iac_api.util.PromisedReturnMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class HeaderAuthKeyInterceptor implements HandlerInterceptor {

    @Value("${header.auth-key}")
    private String authKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestAuthKey = request.getHeader("AuthKey");

        if(ObjectUtils.isEmpty(requestAuthKey) || !requestAuthKey.equals(authKey)){
            throw new AuthKeyInvalidException(PromisedReturnMessage.INVALID_AUTH_KEY);
        }

        return true;
    }
}
