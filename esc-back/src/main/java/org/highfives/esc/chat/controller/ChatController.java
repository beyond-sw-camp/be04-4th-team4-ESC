package org.highfives.esc.chat.controller;

import lombok.RequiredArgsConstructor;
import org.highfives.esc.chat.dto.ChatMessageDTO;
import org.highfives.esc.chat.service.ChatService;
import org.highfives.esc.chat.service.ChatServiceImpl;
import org.highfives.esc.chat.vo.ChatResponseVO;
import org.highfives.esc.chat.vo.ChatResultVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ModelMapper modelMapper;

    /* /pub/~ 받아서 후처리를 할 수 있다. (MessageMapping 으로 받아서 후 처리(저장)하고 SendTo로 구독자에게 넘김)  */
    @MessageMapping("/message")
    @SendTo("/sub/message")
    public ResponseEntity<ChatResultVO> getMessageHandler(
            @Payload ChatResponseVO chatResponseVO // sendMessage의 내용 불러와서 ChatResponseVO 객체로 만듬
            ) {

        System.out.println("chatResponseVO = " + chatResponseVO);
        ChatMessageDTO messageDTO = modelMapper.map(chatResponseVO, ChatMessageDTO.class);

        SimpleDateFormat formatter = new SimpleDateFormat("yy.MM.dd HH:SS");
        String writeDate = formatter.format(LocalDateTime.now());

        messageDTO.setWriteTime(writeDate);

        chatService.getMessage(messageDTO);

        return null;
    }

}
