package org.highfives.esc.studyclub.service;

import org.highfives.esc.crawling.dto.ExamDTO;
import org.highfives.esc.studyclub.dto.GoalDTO;
import org.highfives.esc.studyclub.dto.StudyCategoryDTO;
import org.highfives.esc.studyclub.dto.StudyclubDTO;
import org.highfives.esc.studyclub.dto.StudyclubExamDTO;
import org.highfives.esc.studyclub.vo.StudyclubVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudyclubServiceTest {

    @Autowired
    StudyclubService studyclubService;

    @Test
    @DisplayName("스터디클럽 상세 조회")
    void findStudyclubByIdTest() {
        int clubId = 1;

        StudyclubDTO studyclub = studyclubService.findStudyclubById(clubId);

        assertEquals(studyclub.getId(), clubId);
    }

    @Test
    @DisplayName("스터디클럽 등록")
    void registStudyclubTest() {
        int leaderId = 1;

        StudyclubVO studyclubVO = new StudyclubVO();

        studyclubVO.setName("이름");
        studyclubVO.setIntroduce("소개");
        studyclubVO.setStudyId(1);
        studyclubVO.setExamId(2);
        studyclubVO.setGoalId(1);

        StudyclubDTO studyclub = studyclubService.registStudyclub(studyclubVO, leaderId);
        StudyclubDTO foundStudyclub = studyclubService.findStudyclubById(studyclub.getId());
        StudyclubExamDTO foundStudyclubExam = studyclubService.findStudyclubExamById(studyclub.getId());

        assertNotNull(foundStudyclub);
        assertNotNull(foundStudyclubExam);
        assertEquals(studyclub.getName(), foundStudyclub.getName());
        assertEquals(studyclub.getIntroduce(), foundStudyclub.getIntroduce());
        assertEquals(studyclubVO.getExamId(), foundStudyclubExam.getExamId());
        assertEquals(studyclubVO.getGoalId(), foundStudyclubExam.getGoalId());
    }

    @Test
    @DisplayName("스터디클럽 수정")
    void modifyStudyclubTest() {
        int clubId = 1;

        StudyclubVO studyclubVO = new StudyclubVO();

        studyclubVO.setName("이름2");
        studyclubVO.setIntroduce("소개2");
        studyclubVO.setStudyId(2);
        studyclubVO.setExamId(2);
        studyclubVO.setGoalId(2);

        StudyclubDTO studyclub = studyclubService.modifyStudyclub(clubId, studyclubVO);
        StudyclubDTO foundStudyclub = studyclubService.findStudyclubById(clubId);
        StudyclubExamDTO foundStudyclubExam = studyclubService.findStudyclubExamById(studyclub.getId());

        assertNotNull(foundStudyclub);
        assertEquals(studyclubVO.getName(), foundStudyclub.getName());
        assertEquals(studyclubVO.getIntroduce(), foundStudyclub.getIntroduce());
        assertEquals(studyclubVO.getExamId(), foundStudyclubExam.getExamId());
        assertEquals(studyclubVO.getGoalId(), foundStudyclubExam.getGoalId());
    }

    @Test
    @DisplayName("스터디클럽 삭제")
    void deleteStudyclubTest() {
        int clubId = 1;

        studyclubService.deleteStudyclub(clubId);

        assertEquals(studyclubService.findStudyclubById(clubId).getDeleteStatus(), "Y");
        assertThrows(IllegalArgumentException.class, () -> {
            studyclubService.findStudyclubExamById(clubId);
        });
    }

//    @Test
//    @DisplayName("스터디 카테고리 조회")
//    void findStudyCategoryByIdTest() {
//        int categoryId = 1;
//
//        StudyCategoryDTO studyCategory = studyclubService.findStudyCategoryById(categoryId);
//
//        assertEquals(studyCategory.getId(), categoryId);
//    }

    @Test
    @DisplayName("스터디클럽별 시험일 조회")
    void findExamByClubIdTest() {
        int clubId = 1;

        StudyclubExamDTO studyclubExam = studyclubService.findStudyclubExamById(clubId);
        ExamDTO exam = studyclubService.findExamByClubId(clubId);

        assertEquals(studyclubExam.getExamId(), exam.getId());
    }

    @Test
    @DisplayName("스터디클럽별 목표 조회")
    void findGoalByClubIdTest() {
        int clubId = 1;

        StudyclubExamDTO studyclubExam = studyclubService.findStudyclubExamById(clubId);
        GoalDTO goal = studyclubService.findGoalByClubId(clubId);

        assertEquals(studyclubExam.getGoalId(), goal.getId());
    }
}