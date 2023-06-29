package com.example.spring_iac_api.interceptor;

import com.example.spring_iac_api.domain.Membership;
import com.example.spring_iac_api.exception.AuthKeyInvalidException;
import com.example.spring_iac_api.repository.MembershipRepository;
import com.example.spring_iac_api.util.PromisedReturnMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class HeaderAuthKeyInterceptor implements HandlerInterceptor {

    private final MembershipRepository membershipRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestAuthKey = request.getHeader("AuthKey");

        Optional<Membership> membership = membershipRepository.findMembershipByKey(requestAuthKey);

        if(ObjectUtils.isEmpty(requestAuthKey) || membership.isEmpty()){
            throw new AuthKeyInvalidException(PromisedReturnMessage.INVALID_AUTH_KEY);
        }

        request.setAttribute("service",membership.get().getMembershipName());
        return true;
    }
}
