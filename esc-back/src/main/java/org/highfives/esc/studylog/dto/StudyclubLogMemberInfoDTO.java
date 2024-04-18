package org.highfives.esc.studylog.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudyclubLogMemberInfoDTO {

    private int id;

    private int studyclubId;

    private int memberId;

    private String content;


}
