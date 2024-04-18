package org.highfives.esc.schedule.vo;

import lombok.*;
import org.highfives.esc.schedule.dto.StudyScheduleParticipantDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseStudyScheduleVO {
    private String message;                 // 성공여부 메세지

    private int id;                         // 스터디 일정 id
    private String title;                   // 일정 제목
    private String content;                 // 일정 내용
    private LocalDateTime startDatetime;    // 일정 시작 시간
    private LocalDateTime endDatetime;      // 일정 종료 시간
    private char useStatus;                  // 일정 삭제 여부
    private int writerId;                   // 일정 작성 멤버(리더)
    private int studyclubId;                // 해당 스터디클럽
}
