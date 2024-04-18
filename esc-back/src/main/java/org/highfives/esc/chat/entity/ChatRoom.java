package org.highfives.esc.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "chat_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    @Column
    private int id;

    @Column
    private String roomName;

    @Column
    private String createdDate;

    @Column
    private int roomHostId;

    @Column
    private String useStatus;
}
