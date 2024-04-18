package org.highfives.esc.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class StudyclubMemberDTO {

    private int id;

    private int memberId;

    private int studyclubId;

    private String memberType;

    @Builder
    public StudyclubMemberDTO(int id, int memberId, int studyclubId, String memberType) {
        this.id = id;
        this.memberId = memberId;
        this.studyclubId = studyclubId;
        this.memberType = memberType;
    }
}
