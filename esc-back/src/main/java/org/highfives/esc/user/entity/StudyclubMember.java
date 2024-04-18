package org.highfives.esc.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "studyclub_member")
@Getter
@NoArgsConstructor
@ToString
public class StudyclubMember {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "member_id")
    private int memberId;

    @Column(name = "studyclub_id")
    private int studyclubId;

    @Column(name = "member_type")
    private String memberType;

    @Builder
    public StudyclubMember(int id, int memberId, int studyclubId, String memberType) {
        this.id = id;
        this.memberId = memberId;
        this.studyclubId = studyclubId;
        this.memberType = memberType;
    }
}
