package org.highfives.esc.user.vo;

import lombok.Data;

@Data
public class ResponseUserVO {
    private int id;

    private String name;

    private String email;

    private String nickname;

    private String status;

    private String password;

    private int report_count;

    private int grade;

    private int point;

    private java.util.Date endDate;
}
