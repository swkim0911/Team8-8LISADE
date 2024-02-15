package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class SeatNotFoundException extends GeneralException {

    public SeatNotFoundException(HttpStatus httpStatus, Object result) {
        super(httpStatus, result);
    }
}
