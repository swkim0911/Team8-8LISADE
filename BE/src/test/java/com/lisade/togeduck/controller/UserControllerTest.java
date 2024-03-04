package com.lisade.togeduck.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lisade.togeduck.dto.request.LoginRequest;
import com.lisade.togeduck.dto.request.SignUpRequest;
import com.lisade.togeduck.dto.response.ValidateUserIdResponse;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.exception.EmailAlreadyExistsException;
import com.lisade.togeduck.exception.UserIdAlreadyExistsException;
import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;
import com.lisade.togeduck.global.handler.GlobalExceptionHandler;
import com.lisade.togeduck.global.response.ApiResponse;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.WebRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @DisplayName("회원 아이디 중복 확인 - 아이디 생성 불가능한 경우")
    void checkUserId_X() throws Exception {
        //given
        UserIdAlreadyExistsException exception = new UserIdAlreadyExistsException();
        ResponseEntity<Object> responseEntity = getResponseEntity(exception);

        //when
        when(userService.checkUserId(any(String.class)))
                .thenThrow(exception);

        when(globalExceptionHandler.handleGeneralException(any(GeneralException.class),
                any(WebRequest.class))).thenReturn(responseEntity);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{user_id}", "userId")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(
                        UserIdAlreadyExistsException.class));
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

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    @DisplayName("중복 이메일에 대한 회원가입 실패")
    void joinUser_X() throws Exception {
        //given
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .userId("userId")
                .password("password!12")
                .nickname("nickname")
                .email("right@email.com").build();

        EmailAlreadyExistsException exception = new EmailAlreadyExistsException();
        ResponseEntity<Object> responseEntity = getResponseEntity(exception);

        //when
        when(userService.join(any(SignUpRequest.class))).thenThrow(exception);
        when(globalExceptionHandler.handleGeneralException(any(GeneralException.class),
                any(WebRequest.class))).thenReturn(responseEntity);

        //then
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequest)))
                .andExpect(status().isConflict())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(EmailAlreadyExistsException.class));
    }

    @Test
    @DisplayName("로그인 성공")
    void login_O() throws Exception {
        // given
        LoginRequest loginRequest = LoginRequest.builder()
                .userId("userId")
                .password("password1@")
                .build();
        User mockUser = User.builder().userId("userId").password("password1@").build();


        // when
        when(userService.login(any(LoginRequest.class), any(String.class))).thenReturn(mockUser);

        //then
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).isEqualTo(loginRequest.getUserId()));
    }



    
    // 에러 응답의 상태 코드를 설정하기 위해
    private ResponseEntity<Object> getResponseEntity(GeneralException ex) {
        Error error = ex.getError();
        HttpStatus httpStatus = error.getHttpStatus();
        String message = error.getMessage();
        ApiResponse<Object> body = ApiResponse.of(httpStatus.value(), httpStatus.name(), message);
        HttpHeaders headers = HttpHeaders.EMPTY;
        return new ResponseEntity<>(body, headers, httpStatus);
    }
}
