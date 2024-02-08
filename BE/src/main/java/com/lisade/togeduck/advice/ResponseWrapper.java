package com.lisade.togeduck.advice;

import com.lisade.togeduck.dto.ResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true; //모든 응답이 ResponseBodyAdvice 를 거치기 위해 true
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
        MediaType selectedContentType, Class selectedConverterType,
        ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();
        String message = "회원가입에 성공하였습니다.";
        ResponseDto<Object> responseDto = ResponseDto.builder().status(status).message(message)
            .result(body).build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
