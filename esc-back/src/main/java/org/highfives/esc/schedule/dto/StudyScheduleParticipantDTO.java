package org.highfives.esc.schedule.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudyScheduleParticipantDTO {
    private int id;                 // 스터디 일정 참가자 id
    private int scheduleMemberId;   // 스터디 일정 참가 멤버 id
    private int scheduleId;         // 스터디 일정 id
}
