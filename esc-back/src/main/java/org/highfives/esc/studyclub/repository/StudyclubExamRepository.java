package org.highfives.esc.studyclub.repository;

import org.highfives.esc.studyclub.entity.StudyclubExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StudyclubExamRepository extends JpaRepository<StudyclubExam, Integer> {

    @Query("SELECT studyclubExam FROM StudyclubExam studyclubExam WHERE studyclubExam.clubId = :clubId")
    StudyclubExam findByClubId(@Param("clubId") int clubId);
}
