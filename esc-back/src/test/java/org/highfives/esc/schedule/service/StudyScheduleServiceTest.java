package org.highfives.esc.schedule.service;

import org.highfives.esc.schedule.dto.StudyScheduleDTO;
import org.highfives.esc.schedule.vo.ResponseScheduleVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudyScheduleServiceTest {

    @Autowired
    StudyScheduleService studyScheduleService;

    @Test
    @DisplayName("id로 스터디 일정 조회")
    void findStudyScheduleByIdTest() {
        int id = 1;

        ResponseScheduleVO response = studyScheduleService.findStudyScheduleById(id);

        assertNotNull(response);
    }

    @Test
    @DisplayName("스터디클럽 id로 스터디 일정 조회")
    void findStudyScheduleByStudyculbIdTest() {
        int studyId = 1;

        ArrayList<StudyScheduleDTO> studyScheduleDTOList = studyScheduleService.findStudyScheduleByStudyId(studyId);

        assertNotNull(studyScheduleDTOList);
    }

    @Test
    @DisplayName("스터디 일정 추가")
    void saveStudyScheduleTest() {
        String startTime = "2024-04-03 18:30:00";
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startTime, formatter1);

        String endTime = "2024-04-03 19:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime end = LocalDateTime.parse(endTime, formatter2);

        StudyScheduleDTO studyScheduleDTO = new StudyScheduleDTO(
                "오픽 스피킹 연습 및 피드백",
                "오픽 스피킹 연습 및 피드백 시간입니다.",
                start,
                end,
                'Y',
                6,
                2);

        Integer[] participant = {1, 2, 3, 4};
        ArrayList<Integer> participantList = new ArrayList<>();
        participantList.addAll(Arrays.asList(participant));

        studyScheduleService.saveStudySchedule(studyScheduleDTO, participantList);

        assertNotNull(studyScheduleService.findStudyScheduleById(4));
    }

    @Test
    @DisplayName("스터디 일정 수정")
    @Transactional
    void modifyStudyScheduleTest() {
        String startTime = "2024-04-14 18:30:00";
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startTime, formatter1);

        String endTime = "2024-04-14 19:30:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime end = LocalDateTime.parse(endTime, formatter2);

        StudyScheduleDTO studyScheduleDTO = new StudyScheduleDTO(
                1,
                "토익 단어 스터디",
                "토익 단어 시험을 보겠습니다.",
                start,
                end );

        Integer[] p = {1, 2, 3, 4};
        ArrayList<Integer> participantList = new ArrayList<>();
        participantList.addAll(Arrays.asList(p));

        studyScheduleService.modifyStudySchedule(studyScheduleDTO, participantList);
        assertEquals(studyScheduleDTO.getContent(), studyScheduleService.findStudyScheduleById(1).getContent());
    }

    @Test
    @DisplayName("스터디 일정 삭제")
    @Transactional
    void removeStudyScheduleTest() {
        int id = 1;

        studyScheduleService.removeStudySchedule(id);
        assertEquals('N', studyScheduleService.findStudyScheduleById(id).getUseStatus());
    }
}