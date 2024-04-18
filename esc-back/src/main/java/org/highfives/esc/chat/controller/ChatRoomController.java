package org.highfives.esc.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.highfives.esc.chat.dto.ChatRoomDTO;
import org.highfives.esc.chat.service.ChatRoomService;
import org.highfives.esc.chat.vo.ChatRoomVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat/room")
@Slf4j
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ModelMapper modelMapper;
    private final ChatRoomManager chatRoomManager;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService,
                              ModelMapper modelMapper,
                              ChatRoomManager chatRoomManager) {
        this.chatRoomService = chatRoomService;
        this.modelMapper = modelMapper;
        this.chatRoomManager = chatRoomManager;
    }

    /* 채팅방 조회
     *  채팅하기 클릭 시 기존 채팅방이 있는지 DB에서 확인 후 결과 반환
    * */
    @GetMapping("/id/{roomId}")
    public ResponseEntity<ChatRoomVO> findChatRoom(@PathVariable("roomId") int roomId) throws ClassNotFoundException {

        ChatRoomDTO chatRoomDTO = chatRoomService.findChatRoom(roomId);
        ChatRoomVO result = modelMapper.map(chatRoomDTO, ChatRoomVO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    /* 채팅방 제목으로 해당하는 채팅방 가져오는 함수 */
    @GetMapping("/title/{roomName}")
    public ResponseEntity<List<ChatRoomDTO>> findChatRoomByName(@PathVariable("roomName") String roomName) {

        List<ChatRoomDTO> result = chatRoomService.findChatRoomByName(roomName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomDTO>> findAllRooms() {

        List<ChatRoomDTO> result = new ArrayList<>();

        result = chatRoomService.findAllRooms();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/last")
    public ResponseEntity<Integer> getLastRoom() {

        log.info("여기까지 요청 들어오는지?");
        int result = chatRoomService.findLastRoom();

        log.info("result 결과 : {}", result);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    /* 채팅방 생성
     * 채팅하기 클릭 시 게시글 제목 + 생성자 Id 가지고 와서 채팅방 만듬
    * */
    @PostMapping
    public ResponseEntity<ChatRoomVO> addNewRoom(@RequestBody ChatRoomVO chatRoom) {

        ChatRoomDTO chatRoomDTO = new ChatRoomDTO(
                chatRoom.getId(),
                chatRoom.getRoomName(),
                chatRoom.getRoomHostId(),
                LocalDateTime.now()
        );

        chatRoomDTO = chatRoomService.addNewRoom(chatRoomDTO);

        chatRoomManager.initialChatRoom();
        ChatRoomVO result = modelMapper.map(chatRoomDTO, ChatRoomVO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);

    }

}
