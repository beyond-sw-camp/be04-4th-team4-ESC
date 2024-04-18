package org.highfives.esc.studyclub.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudyclubExamDTO {

    private int id;

    private int clubId;

    private int goalId;

    private int examId;
}
