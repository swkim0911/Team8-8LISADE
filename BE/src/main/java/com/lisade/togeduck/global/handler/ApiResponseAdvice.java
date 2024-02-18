package com.lisade.togeduck.global.handler;

import com.lisade.togeduck.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
        MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request,
        ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int code = servletResponse.getStatus();
        HttpStatus status = HttpStatus.resolve(code);

        if (body instanceof ProblemDetail problemDetail) {
            return ApiResponse.builder().status(code).message(problemDetail.getTitle())
                .result(problemDetail.getDetail()).build();
        }

        if (status.isError()) {
            return ApiResponse.builder().status(code)
                .message(status.name())
                .result(body).build();
        }

        return ApiResponse.builder()
            .status(code)
            .message("정상 응답입니다.")
            .result(body).build();
    }
}
