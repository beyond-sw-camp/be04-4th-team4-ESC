package org.highfives.esc.user.repository;

import org.highfives.esc.user.entity.MemberImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<MemberImage, Integer> {

}
