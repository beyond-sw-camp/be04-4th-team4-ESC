package org.highfives.esc.schedule.vo;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestMemberScheduleListVO {
    ArrayList<RequestMemberScheduleVO> memberSchedules;
}
