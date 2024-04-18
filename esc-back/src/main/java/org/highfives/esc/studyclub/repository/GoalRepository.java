package org.highfives.esc.studyclub.repository;

import org.highfives.esc.studyclub.dto.GoalDTO;
import org.highfives.esc.studyclub.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Integer> {

    @Query("SELECT goal FROM Goal goal WHERE goal.studyId = :studyId")
    List<Goal> findAllByStudyId(@Param("studyId") int studyId);

}
