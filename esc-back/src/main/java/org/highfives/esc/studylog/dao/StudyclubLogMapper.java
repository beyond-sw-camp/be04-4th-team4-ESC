package org.highfives.esc.studylog.dao;

import org.highfives.esc.studylog.dto.StudyclubLogDTO;
import org.highfives.esc.studylog.entity.StudyclubLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudyclubLogMapper {

    StudyclubLogMapper INSTANCE = Mappers.getMapper(StudyclubLogMapper.class);

    @Mapping(target = "id" ,source = "id")
    StudyclubLog studyclubLogDTOTostudyclubLog(StudyclubLogDTO studyclubLogDTOData);


    @Mapping(target = "id", source = "id")
    StudyclubLogDTO studyclubLogTostudyclubLogDTO(StudyclubLog studyclubLog);
}
