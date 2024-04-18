package org.highfives.esc.chat.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.highfives.esc.chat.dto.ChatRoomDTO;
import org.highfives.esc.chat.entity.ChatRoom;
import org.highfives.esc.chat.repository.ChatRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;
    private final ModelMapper modelMapper;
    private Map<Integer, ChatRoomDTO> chatRooms;

    @Autowired
    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, ModelMapper modelMapper) {
        this.chatRoomRepository = chatRoomRepository;
        this.modelMapper = modelMapper;
    }

    // roomId 로 채팅방 찾아서 반환
    @Override
    public ChatRoomDTO findChatRoom(int roomId) throws ClassNotFoundException {

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(ClassNotFoundException :: new);

        ChatRoomDTO result = modelMapper.map(chatRoom, ChatRoomDTO.class);

        log.info("result 로그 { }" , result);

        return result;
    }

    @Override
    public List<ChatRoomDTO> findChatRoomByName(String roomName) {

        List<ChatRoom> list = chatRoomRepository.findByName(roomName);
        List<ChatRoomDTO> result = new ArrayList<>();

        for ( ChatRoom chatRoom : list ) {
            ChatRoomDTO chatRoomDTO = modelMapper.map(chatRoom, ChatRoomDTO.class);

            result.add(chatRoomDTO);
        }

        System.out.println("result = " + result);
        return result;
    }

    // 모든 채팅방 리스트 반환
    @Override
    public List<ChatRoomDTO> findAllRooms() {

        List<ChatRoom> findResult = new ArrayList<>();
        List<ChatRoomDTO> result = new ArrayList<>();
        findResult = chatRoomRepository.findAll();

        for(ChatRoom room : findResult) {
            ChatRoomDTO chatRoomDTO = modelMapper.map(room, ChatRoomDTO.class);

            result.add(chatRoomDTO);
        }

        System.out.println("result = " + result);
        return result;
    }

    @Override
    public int findLastRoom() {

            int result = chatRoomRepository.findLastId();

        return result;
    }

    @Override
    @Transactional
    public ChatRoomDTO addNewRoom(ChatRoomDTO chatRoomDTO) {

        ChatRoom newChatRoom = modelMapper.map(chatRoomDTO, ChatRoom.class);
        newChatRoom.setUseStatus("Y");
        chatRoomRepository.save(newChatRoom);

        ChatRoomDTO result = modelMapper.map(
                chatRoomRepository.findByNameAndHost(chatRoomDTO.getRoomName(), chatRoomDTO.getRoomHostId()),
                ChatRoomDTO.class);

        return result;
    }
}
