package org.highfives.esc.schedule.repository;

import org.highfives.esc.schedule.entity.StudySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface StudyScheduleRepository extends JpaRepository<StudySchedule,Integer> {
    StudySchedule findById(int id);

    ArrayList<StudySchedule> findByStudyclubId(int studyclubId);
}
