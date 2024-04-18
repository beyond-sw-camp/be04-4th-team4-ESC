package org.highfives.esc.chat.service;

import org.highfives.esc.chat.dto.ChatMessageDTO;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{
    @Override
    public void getMessage(ChatMessageDTO messageDTO) {

        // JPA 로 DB에 입력하기

    }
}
