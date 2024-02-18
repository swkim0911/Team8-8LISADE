package com.lisade.togeduck.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Error {
    UN_AUTHENTICATION_ERROR(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),

    FESTIVAL_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "Festival을 찾을 수 없습니다."),
    BUS_NOT_FOIND_ERROR(HttpStatus.NOT_FOUND, "Bus를 찾을 수 없습니다."),
    SEAT_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "Seat를 찾을 수 없습니다."),
    ROUTE_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "Route를 찾을 수 없습니다."),
    STATION_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "Station을 찾을 수 없습니다."),
    FESTIVAL_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "Festival을 찾을 수 없습니다."),
    USER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "아이디 또는 비밀번호가 잘못되었습니다."),
    FESTIVAL_NOT_INCLUDE_ROUTE_ERROR(HttpStatus.NOT_FOUND, "해당 Festival에 Route를 찾을 수 없습니다."),

    ROUTE_ALREADY_EXISTS_ERROR(HttpStatus.CONFLICT, "이미 존재하는 Route입니다."),
    SEAT_ALREADY_REGISTER_ERROR(HttpStatus.CONFLICT, "이미 예약 중인 좌석입니다."),
    EMAIL_ALREADY_REGISTER_ERROR(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    USER_ID_ALREADY_REGISTER_ERROR(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    NICKNAME_ALREADY_REGISTER_ERROR(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String Message;
}
