package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class NotFoundException extends GeneralException {

    public NotFoundException() {
        super(Error.NOT_FOUND_ERROR);
    }

    public NotFoundException(Error error) {
        super(error);
    }
}
