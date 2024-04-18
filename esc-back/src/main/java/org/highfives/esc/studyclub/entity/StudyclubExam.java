package org.highfives.esc.studyclub.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "studyclub_exam")
public class StudyclubExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "club_id")
    private int clubId;

    @Column(name = "goal_id")
    private int goalId;

    @Column(name = "exam_id")
    private int examId;

}
