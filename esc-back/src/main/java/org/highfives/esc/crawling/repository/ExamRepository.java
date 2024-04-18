package org.highfives.esc.crawling.repository;

import org.highfives.esc.crawling.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    
    @Query("SELECT exam FROM Exam exam WHERE exam.studyId = :studyId")
    List<Exam> findAllByStudyId(@Param("studyId") int studyId);
}
