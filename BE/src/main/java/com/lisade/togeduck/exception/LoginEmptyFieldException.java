package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class LoginEmptyFieldException extends GeneralException {

    public LoginEmptyFieldException(HttpStatus httpStatus, Object result) {
        super(httpStatus, result);
    }
}
