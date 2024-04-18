package org.highfives.esc.studyclub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "goal")
public class Goal {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "score")
    private String score;

    @Column(name = "study_id")
    private int studyId;
}
