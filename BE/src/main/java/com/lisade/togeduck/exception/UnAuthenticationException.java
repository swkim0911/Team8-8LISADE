package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class UnAuthenticationException extends GeneralException {

    public UnAuthenticationException(HttpStatus httpStatus,
        Object result) {
        super(httpStatus, result);
    }
}
