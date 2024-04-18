package org.highfives.esc.studylog.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "studyclub_log")
@Getter
@NoArgsConstructor
@ToString
public class StudyclubLog {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "content", length = 255, nullable = false)
    private String content;

    @Column(name = "content_info", nullable = false)
    private String contentInfo;

    @Column(name = "studydate", length = 50, nullable = false)
    private LocalDateTime studydate;

    @Column(name = "enrolldate", length = 50, nullable = false)
    private String enrolldate;

    @Column(name = "studyclub_id", nullable = false)
    private int studyclubId;

    @Column(name = "schedule_id", nullable = false)
    private int scheduleId;

    @Builder
    public StudyclubLog(int id, String content, String contentInfo, LocalDateTime studydate, String enrolldate, int studyclubId, int scheduleId) {
        this.id = id;
        this.content = content;
        this.contentInfo = contentInfo;
        this.studydate = studydate;
        this.enrolldate = enrolldate;
        this.studyclubId = studyclubId;
        this.scheduleId = scheduleId;
    }
}

