package org.highfives.esc.recruit.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "recruit_post")
public class RecruitPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "delete_status")
    private String deleteStatus;

    @Column(name = "recruit_status")
    private String recruitStatus;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_date")
    private String createdDate;

    @Column(name = "writer_id")
    private int writerId;

    @Column(name = "club_id")
    private int clubId;

}
