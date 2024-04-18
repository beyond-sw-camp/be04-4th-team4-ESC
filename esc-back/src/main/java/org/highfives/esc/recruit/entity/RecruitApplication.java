package org.highfives.esc.recruit.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "application_club_recruitment")
public class RecruitApplication {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "recruit_status")
    private String recruitStatus;

    @Column(name = "recruit_user_id")
    private int recruitUserId;

    @Column(name = "recruit_post_id")
    private int recruitPostId;

}
