package org.highfives.esc.studyclub.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudyclubVO {

    private String name;

    private String introduce;

    private int memberLimit;

    private int studyId;

    private int examId;

    private int goalId;
}

