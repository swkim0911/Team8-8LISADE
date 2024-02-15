package com.lisade.togeduck.constant;

import lombok.Getter;

@Getter
public enum SessionConst {

    LOGIN_USER("loginUser");

    private final String sessionName;

    SessionConst(String sessionName) {
        this.sessionName = sessionName;
    }
}
