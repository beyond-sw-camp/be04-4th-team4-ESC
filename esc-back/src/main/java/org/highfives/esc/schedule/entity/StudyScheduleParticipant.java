package org.highfives.esc.schedule.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "study_schedule_participant")
public class StudyScheduleParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;                 // 스터디 일정 참가자 id

    @Column(name = "schedule_member_id")
    private int scheduleMemberId;   // 스터디 일정 참가 멤버 id

    @Column(name = "schedule_id")
    private int scheduleId;         // 스터디 일정 id

    public StudyScheduleParticipant(int scheduleMemberId, int scheduleId) {
        this.scheduleMemberId = scheduleMemberId;
        this.scheduleId = scheduleId;
    }
}
