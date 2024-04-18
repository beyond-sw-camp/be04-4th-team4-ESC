package org.highfives.esc.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Chat {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;                 //채팅 기본키

    @Column(name="chat_message")
    private String chatMessage;     //채팅 내용

    @Column(name="created_date")
    private LocalDateTime createdDate;     //채팅 작성일

    @Column(name="chat_room_id")
    private String chatRoomId;      //채팅방 id

    @Column(name="chat_writer_id")
    private String chatWriterId;    //채팅 작성자 id

}
