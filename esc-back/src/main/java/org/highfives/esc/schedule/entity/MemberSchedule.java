package org.highfives.esc.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "member_schedule")
public class MemberSchedule {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "selected_start_datetime")
    private LocalDateTime startDatetime;

    @Column(name = "selected_end_datetime")
    private LocalDateTime endDatetime;

    @Column(name = "member_id")
    private int memberId;

    @Column(name = "studyclub_id")
    private int studyclubId;
}
