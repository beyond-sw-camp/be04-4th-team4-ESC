package org.highfives.esc.schedule.vo;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestScheduleVO {
    private int id;                         // 스터디 일정 id
    private String title;                   // 일정 제목
    private String content;                 // 일정 내용
    private String start;    // 일정 시작 시간
    private String end;      // 일정 종료 시간
    private String useStatus;                  // 일정 삭제 여부
    private int writerId;                   // 일정 작성 멤버(리더)
    private int studyclubId;                // 해당 스터디클럽

    private ArrayList<Integer> participantList; // 일정 참가자 리스트
}
