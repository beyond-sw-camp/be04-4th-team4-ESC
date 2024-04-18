package org.highfives.esc.studyclub.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GoalDTO {

    private int id;

    private String score;

    private int studyId;
}
