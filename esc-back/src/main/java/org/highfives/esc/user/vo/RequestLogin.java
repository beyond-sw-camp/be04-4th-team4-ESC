package org.highfives.esc.user.vo;

import lombok.Data;

@Data
public class RequestLogin {
    private String email;
    private String password;
}
