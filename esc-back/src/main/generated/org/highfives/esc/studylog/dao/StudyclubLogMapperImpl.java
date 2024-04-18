package org.highfives.esc.studylog.dao;

import javax.annotation.processing.Generated;
import org.highfives.esc.studylog.dto.StudyclubLogDTO;
import org.highfives.esc.studylog.entity.StudyclubLog;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-16T11:55:40+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.3 (ojdkbuild)"
)
@Component
public class StudyclubLogMapperImpl implements StudyclubLogMapper {

    @Override
    public StudyclubLog studyclubLogDTOTostudyclubLog(StudyclubLogDTO studyclubLogDTOData) {
        if ( studyclubLogDTOData == null ) {
            return null;
        }

        StudyclubLog.StudyclubLogBuilder studyclubLog = StudyclubLog.builder();

        studyclubLog.id( studyclubLogDTOData.getId() );
        studyclubLog.content( studyclubLogDTOData.getContent() );
        studyclubLog.studydate( studyclubLogDTOData.getStudydate() );
        studyclubLog.enrolldate( studyclubLogDTOData.getEnrolldate() );
        studyclubLog.studyclubId( studyclubLogDTOData.getStudyclubId() );
        studyclubLog.scheduleId( studyclubLogDTOData.getScheduleId() );

        return studyclubLog.build();
    }

    @Override
    public StudyclubLogDTO studyclubLogTostudyclubLogDTO(StudyclubLog studyclubLog) {
        if ( studyclubLog == null ) {
            return null;
        }

        StudyclubLogDTO.StudyclubLogDTOBuilder studyclubLogDTO = StudyclubLogDTO.builder();

        studyclubLogDTO.id( studyclubLog.getId() );
        studyclubLogDTO.content( studyclubLog.getContent() );
        studyclubLogDTO.studydate( studyclubLog.getStudydate() );
        studyclubLogDTO.enrolldate( studyclubLog.getEnrolldate() );
        studyclubLogDTO.studyclubId( studyclubLog.getStudyclubId() );
        studyclubLogDTO.scheduleId( studyclubLog.getScheduleId() );

        return studyclubLogDTO.build();
    }
}
