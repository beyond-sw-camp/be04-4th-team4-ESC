package org.highfives.esc.user.vo;

import lombok.Data;

@Data
public class RegistUser {
    private String memberName;
    private String memberEmail;
    private String memberNickname;
    private String memberPassword;
}
