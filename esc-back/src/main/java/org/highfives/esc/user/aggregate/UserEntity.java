package org.highfives.esc.user.aggregate;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Data
@Entity
@DynamicInsert
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "nickname")
    private String nickname;

    @Column(name = "status")
    @ColumnDefault("N")
    private String status;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(name = "report_count")
    @ColumnDefault("0")
    private int reportCount;

    @Column(name = "grade")
    @ColumnDefault("0")
    private int grade;

    @Column(name = "point")
    @ColumnDefault("0")
    private int point;

    @Column(name = "end_date")
    private LocalDateTime endDate;
}
