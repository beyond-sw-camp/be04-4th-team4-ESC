package org.highfives.esc.user.repository;

import jakarta.transaction.Transactional;
import org.highfives.esc.user.entity.StudyclubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface StudyclubMemberRepository extends JpaRepository<StudyclubMember, Integer> {


    @Modifying
    @Query(value = "DELETE FROM studyclub_member WHERE member_id = :memberId AND studyclub_id = :studyclubId", nativeQuery = true)
    void deleteByMemberIdAndStudyclubId(@Param("memberId") int memberId, @Param("studyclubId") int studyclubId);
}
