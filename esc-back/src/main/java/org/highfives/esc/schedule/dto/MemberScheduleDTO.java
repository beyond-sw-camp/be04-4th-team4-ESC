package org.highfives.esc.schedule.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberScheduleDTO {
    private int id;                                 // 멤버 일정 시간 id
    private LocalDateTime startDatetime;  // 선택한 일정 시작 시간
    private LocalDateTime endDatetime;    // 선택한 일정 종료 시간
    private int memberId;                          // 해당 멤버
    private int studyclubId;                       // 해당 스터디클럽

    public MemberScheduleDTO(LocalDateTime startDatetime, LocalDateTime endDatetime, int memberId, int studyclubId) {
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.memberId = memberId;
        this.studyclubId = studyclubId;
    }

    public MemberScheduleDTO(int id, LocalDateTime startDatetime, LocalDateTime endDatetime) {
        this.id = id;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
    }
}
