package org.highfives.esc.user.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.highfives.esc.user.aggregate.UserEntity;
import org.highfives.esc.user.dto.StudyclubMemberDTO;
import org.highfives.esc.user.dto.UserDTO;
import org.highfives.esc.user.entity.StudyclubMember;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-16T11:55:40+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.3 (ojdkbuild)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO userToUserDTO(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.id( user.getId() );
        userDTO.name( user.getName() );
        userDTO.email( user.getEmail() );
        userDTO.nickname( user.getNickname() );
        userDTO.status( user.getStatus() );
        userDTO.password( user.getPassword() );
        userDTO.grade( user.getGrade() );
        userDTO.point( user.getPoint() );
        userDTO.endDate( user.getEndDate() );

        return userDTO.build();
    }

    @Override
    public List<UserDTO> userListToUserListDTO(List<UserEntity> userList) {
        if ( userList == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( userList.size() );
        for ( UserEntity userEntity : userList ) {
            list.add( userToUserDTO( userEntity ) );
        }

        return list;
    }

    @Override
    public UserEntity userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( userDTO.getId() );
        userEntity.setName( userDTO.getName() );
        userEntity.setEmail( userDTO.getEmail() );
        userEntity.setNickname( userDTO.getNickname() );
        userEntity.setStatus( userDTO.getStatus() );
        userEntity.setPassword( userDTO.getPassword() );
        userEntity.setReportCount( userDTO.getReportCount() );
        userEntity.setGrade( userDTO.getGrade() );
        userEntity.setPoint( userDTO.getPoint() );
        userEntity.setEndDate( userDTO.getEndDate() );

        return userEntity;
    }

    @Override
    public List<StudyclubMemberDTO> studyclubMemberToStudyclubMemberDTO(List<StudyclubMember> studyclubMembers) {
        if ( studyclubMembers == null ) {
            return null;
        }

        List<StudyclubMemberDTO> list = new ArrayList<StudyclubMemberDTO>( studyclubMembers.size() );
        for ( StudyclubMember studyclubMember : studyclubMembers ) {
            list.add( studyclubMemberToStudyclubMemberDTO1( studyclubMember ) );
        }

        return list;
    }

    protected StudyclubMemberDTO studyclubMemberToStudyclubMemberDTO1(StudyclubMember studyclubMember) {
        if ( studyclubMember == null ) {
            return null;
        }

        StudyclubMemberDTO.StudyclubMemberDTOBuilder studyclubMemberDTO = StudyclubMemberDTO.builder();

        studyclubMemberDTO.id( studyclubMember.getId() );
        studyclubMemberDTO.memberId( studyclubMember.getMemberId() );
        studyclubMemberDTO.studyclubId( studyclubMember.getStudyclubId() );
        studyclubMemberDTO.memberType( studyclubMember.getMemberType() );

        return studyclubMemberDTO.build();
    }
}
