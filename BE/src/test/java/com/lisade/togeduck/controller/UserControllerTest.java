package com.lisade.togeduck.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lisade.togeduck.dto.request.SignUpRequest;
import com.lisade.togeduck.dto.response.ValidateUserIdResponse;
import com.lisade.togeduck.global.handler.ApiResponseHandler;
import com.lisade.togeduck.global.handler.GlobalExceptionHandler;
import com.lisade.togeduck.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Mock
    private GlobalExceptionHandler globalExceptionHandler;


    @BeforeEach
    void setUp() {
        UserController userController = new UserController(userService);

        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(globalExceptionHandler)
                .build();
    }

    @Test
    @DisplayName("회원 아이디 중복 확인 - 아이디 생성 가능한 경우")
    void checkUserId_O() throws Exception {
        //given
        ValidateUserIdResponse mockResponse = ValidateUserIdResponse.builder()
                .userId("사용가능한 아이디입니다.")
                .build();

        when(userService.checkUserId(any(String.class))).thenReturn(mockResponse);

        //when
        ResultActions result = mockMvc.perform(
                get("/users/{user_id}", "userId")
                        .contentType(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("사용가능한 아이디입니다."));
    }



    @Test
    @DisplayName("정상 회원가입 성공")
    void joinUser() throws Exception {
        //given
        SignUpRequest signUpRequest = SignUpRequest.builder()
            .userId("userId")
            .password("password!12")
            .nickname("nickname")
            .email("right@email.com").build();
        //when & then
        when(userService.join(any())).thenReturn(1L);

        MvcResult mvcResult = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"))
                .andReturn();
    }
}
