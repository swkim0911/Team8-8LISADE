package com.lisade.togeduck.exception;

import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;

public class ChatRoomNotFoundException extends GeneralException {

    public ChatRoomNotFoundException() {
        super(Error.CHAT_ROOM_NOT_FOUND_ERROR);
    }

    public ChatRoomNotFoundException(Error error) {
        super(error);
    }
}
