package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class EmailAlreadyExistsException extends GeneralException {

    public EmailAlreadyExistsException() {
        super(Error.EMAIL_ALREADY_REGISTER_ERROR);
    }

    public EmailAlreadyExistsException(Error error) {
        super(error);
    }
}
