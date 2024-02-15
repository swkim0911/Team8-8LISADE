package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class NicknameAlreadyExistsException extends GeneralException {

    public NicknameAlreadyExistsException() {
        super(Error.NICKNAME_ALREADY_REGISTER_ERROR);
    }

    public NicknameAlreadyExistsException(Error error) {
        super(error);
    }
}
