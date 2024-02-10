package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.dto.response.SignUpFailureDto;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.mapper.UserMapper;
import com.lisade.togeduck.repository.UserRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SignUpFailureDto validateSignUp(Errors errors) {
        Map<String, String> validationResult = getSignUpErrorField(errors);
        return UserMapper.toSignUpFailureDto(validationResult);
    }

    @Transactional(readOnly = true)
    public void checkDuplication(SignUpDto signUpDto) {
        checkUserIdDuplication(signUpDto);
        checkNicknameDuplication(signUpDto);
        checkEmailDuplication(signUpDto);
    }

    @Transactional
    public Long join(SignUpDto signUpDto) {
        checkDuplication(signUpDto);
        User user = UserMapper.toUser(signUpDto);
        User saveUser = userRepository.save(user);
        return saveUser.getId();
    }

    private void checkUserIdDuplication(SignUpDto signUpDto) {
        boolean userIdDuplication = userRepository.existsByUserId(signUpDto.getUserId());
        if (userIdDuplication) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    private void checkNicknameDuplication(SignUpDto signUpDto) {
        boolean nicknameDuplication = userRepository.existsByNickname(signUpDto.getNickname());
        if (nicknameDuplication) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    private void checkEmailDuplication(SignUpDto signUpDto) {
        boolean emailDuplication = userRepository.existsByEmail(signUpDto.getEmail());
        if (emailDuplication) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    private Map<String, String> getSignUpErrorField(Errors errors) {
        Map<String, String> validationResult = new HashMap<>();

        for (FieldError fieldError : errors.getFieldErrors()) {
            String field = fieldError.getField();
            validationResult.put(field, fieldError.getDefaultMessage());
        }

        return validationResult;
    }
}
