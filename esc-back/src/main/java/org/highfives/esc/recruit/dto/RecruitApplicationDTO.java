package org.highfives.esc.recruit.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RecruitApplicationDTO {

    private int id;

    private String recruitStatus;

    private int recruitUserId;

    private int recruitPostId;

}
