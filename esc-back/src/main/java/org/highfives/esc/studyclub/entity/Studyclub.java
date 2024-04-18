package org.highfives.esc.studyclub.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "studyclub")
public class Studyclub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "delete_status")
    private String deleteStatus;

    @Column(name = "name")
    private String name;

    @Column(name = "introduce")
    private String introduce;

    @Column(name = "member_count")
    private int memberCount;

    @Column(name = "member_limit")
    private int memberLimit;

    @Column(name = "leader_id")
    private int leaderId;

}

