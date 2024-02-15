package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class SeatAlreadyRegisterException extends GeneralException {

    public SeatAlreadyRegisterException(HttpStatus httpStatus, Object result) {
        super(httpStatus, result);
    }
}
