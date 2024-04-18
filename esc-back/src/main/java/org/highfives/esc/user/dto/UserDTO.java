package org.highfives.esc.user.dto;


import lombok.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDTO {
    private int id;

    private String name;

    private String email;

    private String nickname;

    private String status;

    private String password;

    private int reportCount;

    private int grade;

    private int point;

    private LocalDateTime endDate;

    @Builder
    public UserDTO(int id, String name, String email, String nickname, String status, String password, int report_count, int grade, int point, LocalDateTime endDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.status = status;
        this.password = password;
        this.reportCount = report_count;
        this.grade = grade;
        this.point = point;
        this.endDate = endDate;
    }
}
