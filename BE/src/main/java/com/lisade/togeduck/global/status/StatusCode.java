package com.lisade.togeduck.global.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StatusCode {
    OK(200, "OK", HttpStatus.OK),

    BAD_REQUEST(400, "올바르지 않은 요청입니다.", HttpStatus.BAD_REQUEST),
    FORBIDDEN(403, "접근할 수 없는 요청입니다.", HttpStatus.FORBIDDEN),
    NOT_FOUND(404, "데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    INTERNAL_SERVER_ERROR(500, "서버 에러, 잠시 후에 다시 요청해주세요.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
