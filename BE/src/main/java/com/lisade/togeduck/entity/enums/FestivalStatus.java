package com.lisade.togeduck.entity.enums;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum FestivalStatus {
    RECRUITMENT("1"), TERMINATION("2");

    private final String code;

    FestivalStatus(String code) {
        this.code = code;
    }

    public static FestivalStatus of(String code) {
        if (code == null) {
            throw new IllegalArgumentException("코드가 없습니다.");
        }
        return Arrays.stream(FestivalStatus.values())
            .filter(category -> category.getCode().equals(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("일치하는 Status 코드가 없습니다."));
    }
}
