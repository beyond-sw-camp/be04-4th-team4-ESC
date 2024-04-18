package org.highfives.esc.schedule.repository;

import org.highfives.esc.schedule.dto.MemberScheduleDTO;
import org.highfives.esc.schedule.entity.MemberSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface MemberScheduleRepository extends JpaRepository<MemberSchedule,Integer> {
    MemberSchedule findById(int id);

    ArrayList<MemberSchedule> findByMemberId(int memberId);

    ArrayList<MemberSchedule> findByStudyclubId(int studyclueId);

    ArrayList<MemberSchedule> findByStudyclubIdAndMemberId(int studyclubId, int memberId);
}
