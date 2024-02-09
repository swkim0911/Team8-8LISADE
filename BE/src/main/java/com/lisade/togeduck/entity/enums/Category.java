package com.lisade.togeduck.entity.enums;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Category {
    SPORTS("1"),
    MUSICAL("2"),
    CONCERT("3"),
    FAN_MEETING("4"),
    ANIMATION("5"),
    ETC("6");
    private final String code;

    Category(String code) {
        this.code = code;
    }

    public static Category of(String code) {
        if (code == null) {
            throw new IllegalArgumentException("코드가 없습니다.");
        }
        return Arrays.stream(Category.values())
            .filter(category -> category.getCode().equals(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("일치하는 Category 코드가 없습니다."));
    }
}
