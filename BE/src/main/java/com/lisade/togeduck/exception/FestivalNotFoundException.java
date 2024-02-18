package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class FestivalNotFoundException extends GeneralException {

    public FestivalNotFoundException() {
        super(Error.NOT_FOUND_ERROR);
    }

    public FestivalNotFoundException(Error error) {
        super(error);
    }
}
