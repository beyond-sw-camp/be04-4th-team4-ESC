package org.highfives.esc.schedule.vo;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseStudyScheduleListVO {
    private String message;
    private ArrayList<ResponseStudyScheduleVO> studySchedules;
}
