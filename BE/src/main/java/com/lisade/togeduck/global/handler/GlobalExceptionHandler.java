package com.lisade.togeduck.global.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.lisade.togeduck.annotation.ValidateUserId;
import com.lisade.togeduck.dto.response.ValidateUserIdDto;
import com.lisade.togeduck.global.exception.GeneralException;
import com.lisade.togeduck.global.response.ApiResponse;
import com.lisade.togeduck.mapper.UserMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.metadata.ConstraintDescriptor;
import java.lang.annotation.Annotation;
import java.util.Set;
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

    @Override // 잘못된 메서드 요청 처리 (controller 에 오기 전에 발생해서 @ExceptionHandler 에서 처리 불가)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
        HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status,
        WebRequest request) {
        ApiResponse<Object> body = ApiResponse.of(status.value(), status.toString(), null);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> handleGeneralException(GeneralException generalException,
        WebRequest webRequest) {
        Object result = generalException.getResult();
        HttpStatus httpStatus = generalException.getHttpStatus();

        return makeExceptionResponse(result, generalException, httpStatus, HttpHeaders.EMPTY,
            webRequest);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleViolationException(ConstraintViolationException ex,
        WebRequest webRequest) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) { //todo 확장성으로 일단 만들어 둠. stream 으로 변경 예정
            ConstraintDescriptor<?> constraintDescriptor = violation.getConstraintDescriptor();
            Annotation annotation = constraintDescriptor.getAnnotation();
            if (annotation instanceof ValidateUserId) {
                ValidateUserIdDto validateUserIdDto = UserMapper.toValidateUserIdDto(
                    violation.getMessage());

                return makeExceptionResponse(validateUserIdDto, ex, BAD_REQUEST, HttpHeaders.EMPTY,
                    webRequest);
            }
        }
        return null;
    }

    @ExceptionHandler // controller 에서 발생한 예외중에 GeneralException 외 모든 예외 처리
    public ResponseEntity<Object> handleAllOtherException(Exception exception,
        WebRequest webRequest) {

        return makeInternalExceptionResponse(
            exception,
            INTERNAL_SERVER_ERROR,
            HttpHeaders.EMPTY,
            webRequest);
    }

    // custom exception 처리
    private ResponseEntity<Object> makeExceptionResponse(Object result, Exception exception,
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

    private ResponseEntity<Object> makeInternalExceptionResponse(Exception exception,
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
