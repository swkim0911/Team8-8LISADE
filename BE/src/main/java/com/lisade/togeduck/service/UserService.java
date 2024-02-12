package com.lisade.togeduck.service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.lisade.togeduck.dto.request.LoginDto;
import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.dto.response.LoginEmptyFieldDto;
import com.lisade.togeduck.dto.response.SignUpFailureDto;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.exception.UserNotFoundException;
import com.lisade.togeduck.global.response.ApiResponse;
import com.lisade.togeduck.mapper.UserMapper;
import com.lisade.togeduck.repository.UserRepository;
import com.lisade.togeduck.validator.annotation.ValidateUserId;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SignUpFailureDto validateSignUp(Errors errors) {
        Map<String, String> validationResult = getErrorField(errors);
        return UserMapper.toSignUpFailureDto(validationResult);
    }

    @Transactional
    public Long join(SignUpDto signUpDto) {
        User user = UserMapper.toUser(signUpDto);
        User saveUser = userRepository.save(user);
        return saveUser.getId();
    }

    public LoginEmptyFieldDto validateLogin(Errors errors) {
        Map<String, String> validationResult = getErrorField(errors);
        return UserMapper.toLoginEmptyFieldDto(validationResult);
    }

    @Transactional(readOnly = true)
    public void exist(SignUpDto signUpDto, Errors errors) {
        if (userRepository.existsByUserId(signUpDto.getUserId())) {
            errors.rejectValue("userId", "ID duplication", "이미 존재하는 아이디입니다.");
        }
        if (userRepository.existsByNickname(signUpDto.getNickname())) {
            errors.rejectValue("nickname", "nickname duplication", "이미 존재하는 닉네임입니다.");
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            errors.rejectValue("email", "email duplication", "이미 존재하는 이메일입니다.");
        }
    }

    @Transactional
    public User login(LoginDto loginDto) {
        return userRepository.findByUserIdAndPassword(loginDto.getUserId(),
                loginDto.getPassword())
            .orElseThrow(() -> new UserNotFoundException(BAD_REQUEST, "아이디 또는 비밀번호가 잘못되었습니다."));
    }

    private Map<String, String> getErrorField(Errors errors) {
        Map<String, String> validationResult = new HashMap<>();

        for (FieldError fieldError : errors.getFieldErrors()) {
            String field = fieldError.getField();
            validationResult.put(field, fieldError.getDefaultMessage());
        }

        return validationResult;
    }

    public ResponseEntity<Object> checkUserId(@ValidateUserId String userId) {
        return ResponseEntity.ok(
            ApiResponse.onSuccess(UserMapper.toValidateUserIdDto("사용가능한 아이디입니다.")));
    }

}
