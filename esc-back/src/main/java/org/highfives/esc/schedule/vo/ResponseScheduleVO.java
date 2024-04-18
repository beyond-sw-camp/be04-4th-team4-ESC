package org.highfives.esc.schedule.vo;


import lombok.*;
import org.highfives.esc.schedule.dto.StudyScheduleDTO;
import org.highfives.esc.schedule.dto.StudyScheduleParticipantDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

// 설명. 스터디 일정과 스터디 일정 참여 멤버를 합침
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseScheduleVO {
    private String message;                 // 성공여부 메세지

    private int id;                         // 스터디 일정 id
    private String title;                   // 일정 제목
    private String content;                 // 일정 내용
    private LocalDateTime start;    // 일정 시작 시간
    private LocalDateTime end;      // 일정 종료 시간
    private char useStatus;                  // 일정 삭제 여부
    private int writerId;                   // 일정 작성 멤버(리더)
    private int studyclubId;                // 해당 스터디클럽

    private ArrayList<Integer> studyScheduleParticipantList;
}
