package org.highfives.esc.user.dto;

import lombok.Data;

@Data
public class MemberImageDTO {

    private int id;

    private String original_file_name;
    private String rename_file_name;
    private String saved_url;
    private String userId;
}
