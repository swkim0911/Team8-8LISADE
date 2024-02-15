package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class SeatNotFoundException extends GeneralException {

    public SeatNotFoundException() {
        super(Error.SEAT_NOT_FOUND_ERROR);
    }

    public SeatNotFoundException(Error error) {
        super(error);
    }
}
