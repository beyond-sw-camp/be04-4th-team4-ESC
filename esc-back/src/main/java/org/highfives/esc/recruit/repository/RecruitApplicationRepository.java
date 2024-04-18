package org.highfives.esc.recruit.repository;

import org.highfives.esc.recruit.dto.RecruitApplicationDTO;
import org.highfives.esc.recruit.dto.RecuitApplicationInfoDTO;
import org.highfives.esc.recruit.entity.RecruitApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitApplicationRepository extends JpaRepository<RecruitApplication, Integer> {

    @Query(value = "SELECT new org.highfives.esc.recruit.dto.RecuitApplicationInfoDTO(s1.id, s1.recruitStatus, s1.recruitUserId,s1.recruitPostId,  s2.name) FROM RecruitApplication s1 JOIN UserEntity s2 ON s2.id = s1.recruitUserId WHERE s1.recruitPostId = :recruitPostId")
    List<RecuitApplicationInfoDTO> findAllByRecruitId(@Param("recruitPostId") int recruitPostId);

    @Query("SELECT apply FROM RecruitApplication apply WHERE apply.recruitUserId = :recruitUserId")
    List<RecruitApplication> findAllByUserId(@Param("recruitUserId") int recruitUserId);

    @Query("SELECT apply FROM RecruitApplication apply WHERE apply.recruitPostId = :postId AND apply.recruitUserId = :userId")
    RecruitApplication findByBothId(@Param("postId") int postId, @Param("userId") int userId);
}
