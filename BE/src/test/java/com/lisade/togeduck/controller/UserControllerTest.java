package com.lisade.togeduck.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.service.UserService;
import com.lisade.togeduck.validator.SignUpValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SignUpValidator signUpValidator;

    @Test
    @DisplayName("회원가입 확인")
    void joinUser() throws Exception {
        //given
        SignUpDto signUpDto = SignUpDto.builder()
            .userId("userId")
            .password("password!12")
            .nickname("nickname")
            .email("right@email.com").build();
        //when & then
        when(userService.join(any())).thenReturn(1L);

        mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(signUpDto)));
    }
}