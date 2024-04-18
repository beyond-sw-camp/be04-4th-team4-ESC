package org.highfives.esc.user.vo;

import lombok.Data;

@Data
public class ResponseUser {

    private int id;
    private String name;
    private String email;
    private String password;
    private String nickname;

}