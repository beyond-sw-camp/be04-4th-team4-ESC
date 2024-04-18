package org.highfives.esc.studylog.repository;

import org.highfives.esc.studylog.dto.StudyclubLogInfoDTO;
import org.highfives.esc.studylog.dto.StudyclubLogMemberInfoDTO;
import org.highfives.esc.studylog.entity.StudyclubLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyclubLogRepository extends JpaRepository<StudyclubLog, Integer> {

    @Query(value = "SELECT new org.highfives.esc.studylog.dto.StudyclubLogInfoDTO(s1.id, s1.content,s1.contentInfo, s1.studyclubId) FROM StudyclubLog s1 JOIN Studyclub s2 ON s2.id = s1.studyclubId WHERE s1.studyclubId = :studyclubId")
    List<StudyclubLogInfoDTO> findStudyclubLogById(String studyclubId);

    @Query(value = "SELECT new org.highfives.esc.studylog.dto.StudyclubLogMemberInfoDTO(s3.id, s1.studyclubId, s1.memberId, s3.content) FROM StudyclubMember s1 " +
            "JOIN UserEntity s2 ON s2.id = s1.memberId " +
            "JOIN StudyclubLog s3 ON s3.studyclubId = s1.studyclubId " +
            "WHERE s1.memberId = :member_id")
    List<StudyclubLogMemberInfoDTO> findWritingStudyclubLogById(@Param("member_id") String memberId);


}
