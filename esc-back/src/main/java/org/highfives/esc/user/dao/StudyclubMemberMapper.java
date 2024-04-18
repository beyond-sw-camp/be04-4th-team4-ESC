package org.highfives.esc.user.dao;

import org.highfives.esc.user.dto.StudyclubMemberDTO;
import org.highfives.esc.user.entity.StudyclubMember;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudyclubMemberMapper {
    StudyclubMemberMapper INSTANCE = Mappers.getMapper(StudyclubMemberMapper.class);

    StudyclubMemberDTO studyclubMemberToStudyclubMemberDTO(StudyclubMember studyclubMember);
}
