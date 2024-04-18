package org.highfives.esc.crawling.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "exam")
public class Exam {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "exam_date")
    private String examDate;

    @Column(name = "study_id")
    private int studyId;
}
