package com.lisade.togeduck.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseDto<T> {

    private final int status;

    private final String message;

    private final T result;

    @Builder
    public ResponseDto(int status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }
}
