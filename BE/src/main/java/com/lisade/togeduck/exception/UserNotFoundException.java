package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class UserNotFoundException extends GeneralException {

    public UserNotFoundException() {
        super(Error.USER_NOT_FOUND_ERROR);
    }

    public UserNotFoundException(Error error) {
        super(error);
    }
}
