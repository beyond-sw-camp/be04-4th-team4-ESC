package org.highfives.esc.chat.vo;

import lombok.*;
import org.highfives.esc.chat.dto.MessageType;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatResponseVO {

    private String sender;          // 채팅치는 사람 ID
    private String content;         // 채팅 메시지
    private MessageType type;       // 유저 상태 ( 입장 , 채팅, 퇴장 )
}
