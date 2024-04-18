package org.highfives.esc.studyclub.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudyclubDTO {

    private int id;

    private String deleteStatus;

    private String name;

    private String introduce;

    private int memberCount;

    private int memberLimit;

    private String endDate;

    private int leaderId;

}


