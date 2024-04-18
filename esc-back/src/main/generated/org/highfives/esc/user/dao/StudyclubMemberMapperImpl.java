package org.highfives.esc.user.dao;

import javax.annotation.processing.Generated;
import org.highfives.esc.user.dto.StudyclubMemberDTO;
import org.highfives.esc.user.entity.StudyclubMember;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-16T11:55:40+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.3 (ojdkbuild)"
)
@Component
public class StudyclubMemberMapperImpl implements StudyclubMemberMapper {

    @Override
    public StudyclubMemberDTO studyclubMemberToStudyclubMemberDTO(StudyclubMember studyclubMember) {
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
