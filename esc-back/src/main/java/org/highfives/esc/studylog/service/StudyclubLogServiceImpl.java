package org.highfives.esc.studylog.service;

import lombok.extern.slf4j.Slf4j;
import org.highfives.esc.schedule.entity.StudySchedule;
import org.highfives.esc.schedule.repository.StudyScheduleRepository;
import org.highfives.esc.studylog.dao.StudyclubLogMapper;
import org.highfives.esc.studylog.dto.StudyclubLogDTO;
import org.highfives.esc.studylog.dto.StudyclubLogInfoDTO;
import org.highfives.esc.studylog.dto.StudyclubLogMemberInfoDTO;
import org.highfives.esc.studylog.entity.StudyclubLog;
import org.highfives.esc.studylog.repository.StudyclubLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class StudyclubLogServiceImpl implements StudyclubLogService {

    private final StudyclubLogRepository studyclubLogRepository;
    private final StudyclubLogMapper studyclubLogMapper;

    private final StudyScheduleRepository studyScheduleRepository;

    @Autowired
    public StudyclubLogServiceImpl(StudyclubLogRepository studyclubLogRepository, StudyclubLogMapper studyclubLogMapper, StudyScheduleRepository studyScheduleRepository) {
        this.studyclubLogRepository = studyclubLogRepository;
        this.studyclubLogMapper = studyclubLogMapper;
        this.studyScheduleRepository = studyScheduleRepository;
    }

    @Transactional
    @Override
    public StudyclubLogDTO insertStudyLog(StudyclubLogDTO studyclubLogDTOData) {

        StudySchedule scheduleData = studyScheduleRepository.findById(studyclubLogDTOData.getScheduleId());

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);



        StudyclubLogDTO insertData = StudyclubLogDTO.builder()
                .content(studyclubLogDTOData.getContent())
                .contentInfo(studyclubLogDTOData.getContentInfo())
                .studydate(scheduleData.getStartDatetime())
                .enrolldate(formattedDate)
                .studyclubId(studyclubLogDTOData.getStudyclubId())
                .scheduleId(studyclubLogDTOData.getScheduleId())
                .build();

        StudyclubLog studyclubLog = studyclubLogMapper.studyclubLogDTOTostudyclubLog(insertData);

        studyclubLogRepository.save(studyclubLog);

        log.info("studyclubLog={}", studyclubLog);

        return studyclubLogDTOData;
    }


    @Override
    @Transactional
    public StudyclubLogDTO updateStudyclubLog(StudyclubLogDTO studyclubLogDTOData) {
        log.info("logdata = {}",studyclubLogDTOData.getId());
        log.info("logdata22222 = {}",studyclubLogDTOData);
        StudyclubLog findStudyLogDatabyId = studyclubLogRepository.findById(studyclubLogDTOData.getId()).orElseThrow(IllegalArgumentException::new);

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);

        log.info("데이터 값={}",findStudyLogDatabyId);
        log.info("데이터 값2={}",findStudyLogDatabyId.getStudydate());

        StudyclubLogDTO studyclubLogDTO = StudyclubLogDTO.builder()
                .id(studyclubLogDTOData.getId())
                .content(studyclubLogDTOData.getContent())
                .contentInfo(studyclubLogDTOData.getContentInfo())
                .studydate(findStudyLogDatabyId.getStudydate())
                .enrolldate(formattedDate)
                .studyclubId(findStudyLogDatabyId.getStudyclubId())
                .scheduleId(findStudyLogDatabyId.getScheduleId())
                .build();

        studyclubLogRepository.save(studyclubLogMapper.studyclubLogDTOTostudyclubLog(studyclubLogDTO));

        return studyclubLogDTO;
    }

    @Override
    @Transactional
    public void deleteStudyLog(String id) {

        studyclubLogRepository.deleteById(Integer.valueOf(id));
    }

    @Override
    public StudyclubLogDTO findStudyLogById(String id) {
            StudyclubLog studyclubLog = studyclubLogRepository.findById(Integer.valueOf(id)).orElseThrow(IllegalArgumentException::new);
            return studyclubLogMapper.studyclubLogTostudyclubLogDTO(studyclubLog);

    }

    public List<StudyclubLogInfoDTO> findStudyclubLogById(String studyclubId) {

        List<StudyclubLogInfoDTO> studyclubLog = studyclubLogRepository.findStudyclubLogById(studyclubId);

        return studyclubLog;
    }

    @Override
    public List<StudyclubLogMemberInfoDTO> findWritingStudyclubLogById(String id) {
        List<StudyclubLogMemberInfoDTO> studyclubLog = studyclubLogRepository.findWritingStudyclubLogById(id);

        return studyclubLog;
    }

}
