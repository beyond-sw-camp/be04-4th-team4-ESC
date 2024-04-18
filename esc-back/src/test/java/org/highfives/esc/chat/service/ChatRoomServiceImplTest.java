package org.highfives.esc.chat.service;

import jakarta.transaction.Transactional;
import org.highfives.esc.chat.dto.ChatRoomDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.Stream;


@SpringBootTest
class ChatRoomServiceImplTest {

    @Autowired
    private ChatRoomService chatRoomService;

    static Stream<Arguments> chatRoomInfo() {

        return Stream.of(
                Arguments.of(1),
                Arguments.of(2)
        );
    }

    static Stream<Arguments> chatRoomName() {

        return Stream.of(
                Arguments.of("토플"),
                Arguments.of("토익")
        );
    }

    static Stream<Arguments> chatRoomDTO() {

        return Stream.of(
                Arguments.of(new ChatRoomDTO("토익 990점 목표방!!", 3,  LocalDateTime.now())),
                Arguments.of(new ChatRoomDTO("IELTS 보실 분 모아봐요", 4,  LocalDateTime.now()))
        );
    }

    @DisplayName("채팅방 ID로 채팅방 조회")
    @ParameterizedTest
    @MethodSource("chatRoomInfo")
    void findChatRoomTest(int roomId) {

        Assertions.assertDoesNotThrow(
                () -> chatRoomService.findChatRoom(roomId)
        );
    }

    @DisplayName("채팅방 제목으로 채팅방 조회")
    @ParameterizedTest
    @MethodSource("chatRoomName")
    void findChatRoomByNameTest(String roomName) {

        Assertions.assertDoesNotThrow(
                () -> chatRoomService.findChatRoomByName(roomName)
        );
    }

    @DisplayName("마지막 채팅방 번호 조회(채팅방 생성 시 사용)")
    @Test
    void findLastRoomTest() {

        Assertions.assertDoesNotThrow(
                () -> chatRoomService.findLastRoom()
        );
    }

    @DisplayName("모든 채팅방 목록 반환")
    @Test
    void findAllRoomsTest() {

        Assertions.assertDoesNotThrow(
                () -> chatRoomService.findAllRooms()
        );
    }

    @DisplayName("ChatRoomDTO 가지고 새 채팅방 생성")
    @ParameterizedTest
    @MethodSource("chatRoomDTO")
    @Transactional
    void addNewRoomTest(ChatRoomDTO chatRoomDTO) {

        Assertions.assertDoesNotThrow(
                () -> chatRoomService.addNewRoom(chatRoomDTO)
        );
    }
}