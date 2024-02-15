package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class SeatAlreadyRegisterException extends GeneralException {

    public SeatAlreadyRegisterException() {
        super(Error.SEAT_ALREADY_REGISTER_ERROR);
    }

    public SeatAlreadyRegisterException(Error error) {
        super(error);
    }
}
