package org.highfives.esc.schedule.vo;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMemberScheduleVO {
    String message;
    private int id;                                 // 멤버 일정 시간 id
    private LocalDateTime startDatetime;  // 선택한 일정 시작 시간
    private LocalDateTime endDatetime;    // 선택한 일정 종료 시간
    private int memberId;                          // 해당 멤버
    private int studyclubId;                       // 해당 스터디클럽
}
