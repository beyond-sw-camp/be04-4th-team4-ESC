package org.highfives.esc.studylog.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class StudyclubLogDTO {

    private int id;

    private String content;

    private String contentInfo;

    private LocalDateTime studydate;

    private String enrolldate;

    private int studyclubId;

    private int scheduleId;

    @Builder
    public StudyclubLogDTO(int id, String content, String contentInfo, LocalDateTime studydate, String enrolldate, int studyclubId, int scheduleId) {
        this.id = id;
        this.content = content;
        this.contentInfo = contentInfo;
        this.studydate = studydate;
        this.enrolldate = enrolldate;
        this.studyclubId = studyclubId;
        this.scheduleId = scheduleId;
    }
}
