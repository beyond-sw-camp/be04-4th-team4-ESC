package org.highfives.esc.user.controller;

import org.assertj.core.api.Assertions;
import org.highfives.esc.user.dto.StudyclubInfoDTO;
import org.highfives.esc.user.dto.UserDTO;
import org.highfives.esc.user.dto.UserInfoDTO;
import org.highfives.esc.user.repository.UserRepository;
import org.highfives.esc.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTests {

    private final UserService userService;

    @Autowired
    public UserControllerTests(UserService userService) {
        this.userService = userService;
    }

    @DisplayName("userId를 이용한 회원 조회 테스트")
    @Test
    @Transactional
    void findUserById() {
        // Given
        int id = 1;

        // When
        UserDTO userById = userService.findUserById(String.valueOf(id));

        // Then
        assertThat(userById).isNotNull();
        assertThat(userById.getId()).isEqualTo(id);
        assertThat(userById.getName()).isEqualTo("홍길동");
    }

    @DisplayName("회원 리스트 조회 테스트")
    @Test
    @Transactional
    void findUserList() {
        // Given

        // When
        List<UserDTO> userList = userService.findUserList();

        // Then
        assertThat(userList).isNotNull();
    }

    @DisplayName("회원 정지 기능 테스트")
    @Test
    @Transactional
    void banUserById() {

        // Given
        UserDTO userDTO = UserDTO.builder()
                .id(1)
                .status("Y")
                .build();

        // When
        UserDTO banTest = userService.banUserById(userDTO);

        // Then
        assertThat(banTest).isNotNull();
        assertThat(banTest.getStatus()).isEqualTo("Y");


    }

    @DisplayName("회원 정보 수정 테스트")
    @Test
    @Transactional
    void updateUserInfoById() {

        // Given
        UserDTO userDTO = UserDTO.builder()
                .id(1)
                .name("유재석")
                .point(500)
                .grade(4)
                .password("pass012")
                .email("ujae@gmail.com")
                .nickname("메뚜기")
                .report_count(0)
                .status("N")
                .build();

        // When

        UserDTO updateTest = userService.updateUserInfoById(userDTO);

        // Then
        assertThat(updateTest).isNotNull();
        assertThat(updateTest.getGrade()).isEqualTo(4);
        assertThat(updateTest.getPassword()).isEqualTo("pass012");
        assertThat(updateTest.getPoint()).isEqualTo(500);
    }

    @DisplayName("회원이 참여한 스터디 클럽 조회 테스트")
    @Test
    @Transactional
    void findJoinStudyClubById() {

        // Given
        int memberId = 1;

        // When
        List<StudyclubInfoDTO> joinStudyClubById = userService.findJoinStudyClubById(String.valueOf(memberId));

        // Then

            assertThat(joinStudyClubById).isNotNull();


    }

    @DisplayName("회원의 포인트 변경 테스트")
    @Test
    @Transactional
    void getUserPoint() {

        // Given
        int point = 4300;

        UserDTO userDTO = UserDTO.builder()
                .id(1)
                .point(point)
                .build();

        // When
        UserDTO userPointTest = userService.getUserPoint(userDTO);

        // Then

        assertThat(userPointTest.getPoint()).isEqualTo(300);
        assertThat(userPointTest.getGrade()).isEqualTo(8);

    }

    @DisplayName("회원 탈퇴 테스트")
    @Test
    @Transactional
    void userWithdrawalById() {

        // Given
        UserDTO userDTO = UserDTO.builder()
                .id(1)
                .status("Y")
                .build();

        // When
        UserDTO userWithdrawalTest = userService.userWithdrawalById(userDTO);

        // Then

        assertThat(userWithdrawalTest).isNotNull();
        assertThat(userWithdrawalTest.getStatus()).isEqualTo(userDTO.getStatus());
        assertThat(userWithdrawalTest.getId()).isEqualTo(userDTO.getId());

    }

    @DisplayName("회원 가입 테스트")
    @Test
    @Transactional
    void singUp() {

        // Given
        UserDTO userDTO = UserDTO.builder()
                .email("test@test.com")
                .name("홍길동")
                .grade(0)
                .point(0)
                .report_count(0)
                .nickname("길동홍")
                .password("pass01")
                .status("N")
                .build();


        // When
        UserDTO userSignUpTest = userService.signUp(userDTO);

        // Then
        assertThat(userSignUpTest).isNotNull();
        assertThat(userSignUpTest.getName()).isEqualTo(userDTO.getName());
        assertThat(userSignUpTest.getNickname()).isEqualTo(userDTO.getNickname());

    }

    @DisplayName("스터디클럽 참여 회원 이름 테스트")
    @Test
    @Transactional
    void findJoinMemberAndNameById() {

        // Given
        int studyclubId = 1;

        // When
        List<UserInfoDTO> joinMemberAndNameById = userService.findJoinMemberAndNameById(String.valueOf(studyclubId));

        // Then

        assertThat(joinMemberAndNameById.get(0).getName()).isEqualTo("홍길동");
        assertThat(joinMemberAndNameById.get(1).getName()).isEqualTo("김철수");
        assertThat(joinMemberAndNameById.get(2).getName()).isEqualTo("이영희");


    }
}