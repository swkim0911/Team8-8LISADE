package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class InvalidLoginInfoException extends GeneralException {

    public InvalidLoginInfoException(HttpStatus httpStatus, Object result) {
        super(httpStatus, result);
    }
}
