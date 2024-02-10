package com.lisade.togeduck.global.handler;

import com.lisade.togeduck.global.exception.GeneralException;
import com.lisade.togeduck.global.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler() // custom exception 외 모든 예외 처리
    public ResponseEntity<Object> exception(GeneralException generalException,
        WebRequest webRequest) {
        return handleExceptionInternal(
            generalException,
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

    private ResponseEntity<Object> handleExceptionInternal(Exception exception,
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
