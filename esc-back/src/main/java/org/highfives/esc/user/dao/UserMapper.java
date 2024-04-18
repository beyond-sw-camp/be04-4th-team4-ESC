package org.highfives.esc.user.dao;

import org.highfives.esc.user.aggregate.UserEntity;
import org.highfives.esc.user.dto.StudyclubMemberDTO;
import org.highfives.esc.user.dto.UserDTO;
import org.highfives.esc.user.entity.StudyclubMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(UserEntity user);

    @Mapping(target = "id", source = "id")
    List<UserDTO> userListToUserListDTO(List<UserEntity> userList);

    UserEntity userDTOToUser(UserDTO userDTO);

    List<StudyclubMemberDTO> studyclubMemberToStudyclubMemberDTO(List<StudyclubMember> studyclubMembers);
}
