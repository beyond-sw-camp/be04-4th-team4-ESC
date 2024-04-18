package org.highfives.esc.user.controller;

import org.assertj.core.api.Assertions;
import org.highfives.esc.studyclub.service.StudyclubService;
import org.highfives.esc.user.dto.StudyclubMemberDTO;
import org.highfives.esc.user.dto.UserDTO;
import org.highfives.esc.user.service.StudyclubMemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudyclubMemberControllerTests {

    private final StudyclubMemberService studyclubMemberService;

    @Autowired
    public StudyclubMemberControllerTests(StudyclubMemberService studyclubMemberService) {
        this.studyclubMemberService = studyclubMemberService;
    }

    @DisplayName("스터디 클럽 멤버 추가 기능 테스트")
    @Test
    @Transactional
    void insertMemberById() {
        // Given
        StudyclubMemberDTO studyclubMemberDTO = StudyclubMemberDTO.builder()
                .memberId(1)
                .studyclubId(1)
                .memberType("T")
                .build();

        // When
        StudyclubMemberDTO studyclubMemberTest = studyclubMemberService.insetMemberById(studyclubMemberDTO);

        // Then
        assertThat(studyclubMemberTest).isNotNull();
        assertThat(studyclubMemberTest.getMemberId()).isEqualTo(1);
        assertThat(studyclubMemberTest.getStudyclubId()).isEqualTo(1);

    }

    @DisplayName("스터디 클럽 멤버 삭제 기능 테스트")
    @Test
    @Transactional
    void deleteByMemberIdAndStudyclubId() {
        // Given
        int memberId = 1;
        int studyclubId = 1;
        StudyclubMemberDTO studyclubMemberDTO = StudyclubMemberDTO.builder()
                .memberId(memberId)
                .studyclubId(studyclubId)
                .build();


        // When
        studyclubMemberService.deleteByMemberIdAndStudyclubId(studyclubMemberDTO);

        // Then



    }
}