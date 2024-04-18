package org.highfives.esc.studyclub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "study_category")
public class StudyCategory {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "study_category_name")
    private String studyName;

}
