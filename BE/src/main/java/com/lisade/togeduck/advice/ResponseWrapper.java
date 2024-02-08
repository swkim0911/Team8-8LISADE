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
        HttpStatus httpStatus = HttpStatus.valueOf(servletResponse.getStatus());
        String statusMessage = httpStatus.name();
        ResponseDto<Object> responseDto = ResponseDto.builder().status(httpStatus.value())
            .message(statusMessage).result(body).build();
        return new ResponseEntity<>(responseDto, httpStatus); //todo 상태코드 안 넣어도 받은 응답 상태코드가 그대로 가는지
    }
}
