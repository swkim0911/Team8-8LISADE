package com.lisade.togeduck.service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.lisade.togeduck.annotation.ValidateUserId;
import com.lisade.togeduck.dto.request.LoginDto;
import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.dto.response.LoginEmptyFieldDto;
import com.lisade.togeduck.dto.response.SignUpFailureDto;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.exception.InvalidLoginException;
import com.lisade.togeduck.global.response.ApiResponse;
import com.lisade.togeduck.mapper.UserMapper;
import com.lisade.togeduck.repository.UserRepository;
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

    @Transactional
    public User login(LoginDto loginDto) {
        return userRepository.findByUserIdAndPassword(loginDto.getUserId(),
                loginDto.getPassword())
            .orElseThrow(() -> new InvalidLoginException(BAD_REQUEST, "아이디 또는 비밀번호가 잘못되었습니다."));
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
