package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.dto.response.SignUpFailureDto;
import com.lisade.togeduck.entity.User;
import java.util.Map;

public class UserMapper {

    public static SignUpFailureDto toSignUpFailureDto(Map<String, String> validationResult) {
        return SignUpFailureDto.builder()
            .userId(validationResult.get("userId"))
            .password(validationResult.get("password"))
            .nickname(validationResult.get("nickname"))
            .email(validationResult.get("email"))
            .build();
    }

    public static User toUser(SignUpDto signUpDto) {
        return User.builder()
            .userId(signUpDto.getUserId())
            .password(signUpDto.getPassword())
            .nickname(signUpDto.getNickname())
            .email(signUpDto.getEmail())
            .build();
    }

}
