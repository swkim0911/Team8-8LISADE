package com.lisade.togeduck.entity.enums;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Category {
    SPORTS("1", "스포츠"),
    MUSICAL("2", "뮤지컬"),
    CONCERT("3", "콘서트"),
    FAN_MEETING("4", "팬미팅"),
    ANIMATION("5", "애니메이션"),
    ETC("6", "기타");
    private final String code;
    private final String type;

    Category(String code, String type) {
        this.code = code;
        this.type = type;
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
