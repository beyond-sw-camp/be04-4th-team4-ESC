package org.highfives.esc.recruit.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RecruitPostDTO {

    private int id;

    private String title;

    private String content;

    private String createdDate;

    private String deleteStatus;

    private String recruitStatus;

    private int writerId;

    private int clubId;
}
