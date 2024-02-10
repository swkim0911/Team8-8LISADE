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

    @Transactional
    public Long join(SignUpDto signUpDto) {
        User user = UserMapper.toUser(signUpDto);
        User saveUser = userRepository.save(user);
        return saveUser.getId();
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
