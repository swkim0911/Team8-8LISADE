package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.dto.response.LoginFailureDto;
import com.lisade.togeduck.dto.response.SignUpFailureDto;
import com.lisade.togeduck.dto.response.ValidateUserIdDto;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.enums.AuthorityType;
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

    public static LoginFailureDto toLoginFailureDto(Map<String, String> validationResult) {
        return LoginFailureDto.builder()
            .userId(validationResult.get("usrId"))
            .password(validationResult.get("password"))
            .build();
    }

    public static User toUser(SignUpDto signUpDto) {
        return User.builder()
            .userId(signUpDto.getUserId())
            .password(signUpDto.getPassword())
            .nickname(signUpDto.getNickname())
            .email(signUpDto.getEmail())
            .authority(AuthorityType.ROLE_USER)
            .build();
    }

    public static ValidateUserIdDto toValidateUserIdDto(String userIdMessage) {
        return ValidateUserIdDto.builder()
            .userId(userIdMessage)
            .build();
    }
}
