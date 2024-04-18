package org.highfives.esc.crawling.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ExamDTO {

    private int id;

    private String examDate;

    private int studyId;
}
