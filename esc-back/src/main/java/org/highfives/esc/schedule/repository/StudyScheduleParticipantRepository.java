package org.highfives.esc.schedule.repository;

import org.highfives.esc.schedule.entity.StudyScheduleParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface StudyScheduleParticipantRepository extends JpaRepository<StudyScheduleParticipant,Integer> {

    ArrayList<StudyScheduleParticipant> findByScheduleId(int scheduleId);

    void deleteAllByScheduleId(int scheduleId);
}
