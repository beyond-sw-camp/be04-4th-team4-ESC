package org.highfives.esc.studylog.service;

import org.highfives.esc.studylog.dto.StudyclubLogDTO;
import org.highfives.esc.studylog.dto.StudyclubLogInfoDTO;
import org.highfives.esc.studylog.dto.StudyclubLogMemberInfoDTO;

import java.util.List;

public interface StudyclubLogService {
    StudyclubLogDTO insertStudyLog(StudyclubLogDTO studyclubLogDTOData);

    StudyclubLogDTO updateStudyclubLog(StudyclubLogDTO studyclubLogDTOData) throws IllegalAccessException;


    void deleteStudyLog(String id);

    StudyclubLogDTO findStudyLogById(String id);

    List<StudyclubLogInfoDTO> findStudyclubLogById(String studyclubId);

    List<StudyclubLogMemberInfoDTO> findWritingStudyclubLogById(String id);
}
