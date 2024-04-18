package org.highfives.esc.chat.repository;

import org.highfives.esc.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository <Chat, Integer> {

    // JpaRepository<> 상속받아야함 -> 엔터티 정의 필요  DB에 뭐를 저장할건지?


}
