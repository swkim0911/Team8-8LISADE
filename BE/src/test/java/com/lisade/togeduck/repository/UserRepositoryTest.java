package com.lisade.togeduck.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자가 DB에 잘 저장되는지 확인")
    void saveUser() {
        //given
        SignUpDto signUpDto = SignUpDto.builder()
            .userId("testId")
            .password("password12@")
            .nickname("nickname")
            .email("right@email.com")
            .build();
        User user = UserMapper.toUser(signUpDto);
        //when
        User savedUser = userRepository.save(user);

        //then
        assertThat(savedUser.getUserId()).isEqualTo(user.getUserId());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(savedUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("DB에 저장된 사용자 정보가 제대로 조회되는지 확인")
    void existId() {
        //given
        SignUpDto signUpDto = SignUpDto.builder()
            .userId("testId")
            .password("password12@")
            .nickname("nickname")
            .email("right@email.com")
            .build();
        User user = UserMapper.toUser(signUpDto);

        //when
        userRepository.save(user);
        boolean existId = userRepository.existsByUserId(signUpDto.getUserId());
        boolean existNickname = userRepository.existsByNickname(signUpDto.getNickname());
        boolean existEmail = userRepository.existsByEmail(signUpDto.getEmail());

        //then
        assertThat(existId).isTrue();
        assertThat(existNickname).isTrue();
        assertThat(existEmail).isTrue();
    }
}