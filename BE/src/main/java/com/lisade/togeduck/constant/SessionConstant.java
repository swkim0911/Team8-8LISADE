package com.lisade.togeduck.constant;

public enum SessionConst {

    LOGIN_USER("loginUser");

    private String sessionName;

    SessionConst(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionName() {
        return this.sessionName;
    }
}
