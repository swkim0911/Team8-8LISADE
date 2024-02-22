package com.lisade.togeduck.resolver;

import static com.lisade.togeduck.constant.SessionConst.LOGIN_USER;

import com.lisade.togeduck.annotation.Login;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.exception.UnAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation =
            parameter.hasParameterAnnotation(Login.class);
        boolean hasUserType =
            User.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation
            && hasUserType; // @Login 애노테이션, User 타입이면 LoginArgumentResolver 적용
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if (session == null) {
            log.error("UnAuthenticationException");
            throw new UnAuthenticationException();
        }
        return session.getAttribute(LOGIN_USER.getSessionName());
    }
}
