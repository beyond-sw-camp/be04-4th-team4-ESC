package org.highfives.esc.schedule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "study_schedule")
public class StudySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;                         // 스터디 일정 id

    @Column(name = "title")
    private String title;                   // 일정 제목

    @Column(name = "content")
    private String content;                 // 일정 내용

    @Column(name = "start_datetime")
    private LocalDateTime startDatetime;    // 일정 시작 시간

    @Column(name = "end_datetime")
    private LocalDateTime endDatetime;      // 일정 종료 시간

    @Column(name = "use_status")
    private char useStatus;                  // 일정 삭제 여부

    @Column(name = "schedule_writer_id")
    private int writerId;                   // 일정 작성 멤버(리더)

    @Column(name = "studyclub_id")
    private int studyclubId;                // 해당 스터디클럽

    public StudySchedule(String title, String content, LocalDateTime startDatetime, LocalDateTime endDatetime, char useStatus, int writerId, int studyclubId) {
        this.title = title;
        this.content = content;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.useStatus = useStatus;
        this.writerId = writerId;
        this.studyclubId = studyclubId;
    }
}
