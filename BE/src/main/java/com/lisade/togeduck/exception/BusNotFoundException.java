package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class BusNotFoundException extends GeneralException {

    public BusNotFoundException() {
        super(Error.BUS_NOT_FOIND_ERROR);
    }

    public BusNotFoundException(Error error) {
        super(error);
    }
}
