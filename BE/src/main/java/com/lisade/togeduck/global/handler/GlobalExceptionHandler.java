package com.lisade.togeduck.global.handler;

import com.lisade.togeduck.global.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice()
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override // 잘못된 메서드 요청
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
        HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status,
        WebRequest request) {
        ApiResponse<Object> body = ApiResponse.of(status.value(), status.toString(), null);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(Exception.class) // controller 에서 발생한 예외중에 custom exception 외 모든 예외 처리
    public ResponseEntity<Object> exception(Exception exception,
        WebRequest webRequest) {
        return handleInternalException(
            exception,
            HttpStatus.INTERNAL_SERVER_ERROR,
            HttpHeaders.EMPTY,
            webRequest);
    }

    // custom exception 처리
    private ResponseEntity<Object> handleCustomException(Object result, Exception exception,
        HttpStatus httpStatus,
        HttpHeaders headers, WebRequest webRequest) {
        ApiResponse<Object> body = ApiResponse.of(httpStatus.value(), httpStatus.name(), result);

        return super.handleExceptionInternal(
            exception,
            body,
            headers,
            httpStatus,
            webRequest);
    }

    private ResponseEntity<Object> handleInternalException(Exception exception,
        HttpStatus httpStatus,
        HttpHeaders headers, WebRequest webRequest) {
        ApiResponse<Object> body = ApiResponse.of(httpStatus.value(), httpStatus.name(), null);

        return super.handleExceptionInternal(
            exception,
            body,
            headers,
            httpStatus,
            webRequest);
    }
}
