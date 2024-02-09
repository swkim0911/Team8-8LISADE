package com.lisade.togeduck.controller;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.service.UserService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Object> signUp(@RequestBody @Valid SignUpDto signUpDto, Errors errors) {

        if (errors.hasErrors()) {
            Map<String, String> validationResult = userService.validateSignUp(errors);
            
        }
    }
}
