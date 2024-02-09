package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.SignUpFailureDto;
import java.util.Map;

public class UserMapper {

    public static SignUpFailureDto toSignUpFailureDto(Map<String, String> validationResult) {
        return SignUpFailureDto.builder().
            userId(validationResult.get("userId"))
            .password(validationResult.get("password"))
            .nickname(validationResult.get("nickname"))
            .email(validationResult.get("email"))
            .build();
    }

}
