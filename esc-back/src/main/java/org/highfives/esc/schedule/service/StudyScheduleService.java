package org.highfives.esc.schedule.service;

import org.highfives.esc.schedule.dto.StudyScheduleDTO;
import org.highfives.esc.schedule.entity.StudySchedule;
import org.highfives.esc.schedule.entity.StudyScheduleParticipant;
import org.highfives.esc.schedule.repository.StudyScheduleParticipantRepository;
import org.highfives.esc.schedule.repository.StudyScheduleRepository;
import org.highfives.esc.schedule.vo.ResponseScheduleVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class StudyScheduleService {

    private final ModelMapper mapper;
    private final StudyScheduleRepository studyScheduleRepository;
    private final StudyScheduleParticipantRepository studyScheduleParticipantRepository;

    @Autowired
    public StudyScheduleService(ModelMapper mapper, StudyScheduleRepository studyScheduleRepository
            , StudyScheduleParticipantRepository studyScheduleParticipantRepository) {
        this.mapper = mapper;
        this.studyScheduleRepository = studyScheduleRepository;
        this.studyScheduleParticipantRepository = studyScheduleParticipantRepository;
    }

    /* 설명. id로 스터디 일정 조회 */
    public ResponseScheduleVO findStudyScheduleById(int id) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        /* 설명. id로 스터디 일정 조회 */
        StudySchedule studySchedule = studyScheduleRepository.findById(id);

        if(studySchedule != null){
            ResponseScheduleVO response = new ResponseScheduleVO();

//            StudyScheduleDTO studyScheduleDTO = mapper.map(studySchedule, StudyScheduleDTO.class);
            response.setId(studySchedule.getId());
            response.setTitle(studySchedule.getTitle());
            response.setContent(studySchedule.getContent());
            response.setStart(studySchedule.getStartDatetime());
            response.setEnd(studySchedule.getEndDatetime());
            response.setUseStatus(studySchedule.getUseStatus());
            response.setStudyclubId(studySchedule.getStudyclubId());
            response.setWriterId(studySchedule.getWriterId());

            /* 설명. 스터디 일정 id로 스터디 일정 참여 멤버 조회 */
            ArrayList<StudyScheduleParticipant> studyScheduleParticipantList = studyScheduleParticipantRepository.findByScheduleId(id);

            ArrayList<Integer> scheduleParticipantList = new ArrayList<>();
            if(!studyScheduleParticipantList.isEmpty()){
                for(StudyScheduleParticipant studyScheduleParticipant : studyScheduleParticipantList){
                    scheduleParticipantList.add(studyScheduleParticipant.getScheduleMemberId());
                }
                response.setStudyScheduleParticipantList(scheduleParticipantList);
            } else {
                response.setStudyScheduleParticipantList(scheduleParticipantList);
            }

            return response;
        } else {
            return null;
        }
    }

    /* 설명. 스터디클럽 id로 스터디 일정 조회 */
    public ArrayList<StudyScheduleDTO> findStudyScheduleByStudyId(int studyclubId) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ArrayList<StudySchedule> studyScheduleList = studyScheduleRepository.findByStudyclubId(studyclubId);

        ArrayList<StudyScheduleDTO> studyScheduleDTOList = new ArrayList<>();
        for (StudySchedule studySchedule: studyScheduleList) {
            studyScheduleDTOList.add(mapper.map(studySchedule, StudyScheduleDTO.class));
        }

        return studyScheduleDTOList;
    }

    /* 설명. 스터디클럽 일정 추가 */
    @Transactional
    public void saveStudySchedule(StudyScheduleDTO studyScheduleDTO, ArrayList<Integer> participantList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // 설명. 스터디 일정 추가
        StudySchedule studySchedule = mapper.map(studyScheduleDTO, StudySchedule.class);
        StudySchedule savedStudySchedule = studyScheduleRepository.save(studySchedule);

        int scheduleId = savedStudySchedule.getId();
        // 설명. 스터디 일정 참여자 추가
        if(!participantList.isEmpty()) {
            for (int p : participantList) {
                StudyScheduleParticipant studyScheduleParticipant = new StudyScheduleParticipant();
                studyScheduleParticipant.setScheduleMemberId(p);
                studyScheduleParticipant.setScheduleId(scheduleId);
                studyScheduleParticipantRepository.save(studyScheduleParticipant);
            }
        }
    }

    /* 스터디클럽 일정 수정 */
    @Transactional
    public void modifyStudySchedule(StudyScheduleDTO studyScheduleDTO, ArrayList<Integer> participantList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // 설명. 스터디 일정 수정
        StudySchedule studySchedule = studyScheduleRepository.findById(studyScheduleDTO.getId());
        studySchedule.setTitle(studyScheduleDTO.getTitle());
        studySchedule.setContent(studyScheduleDTO.getContent());
        studySchedule.setStartDatetime(studySchedule.getStartDatetime());
        studySchedule.setEndDatetime(studyScheduleDTO.getEndDatetime());

        int scheduleId = studyScheduleDTO.getId();
        ArrayList<StudyScheduleParticipant> studyScheduleParticipantList = studyScheduleParticipantRepository.findByScheduleId(scheduleId);

        // 설명. 스터디 일정 참가자 수정
        // 설명. 기존 참가자 삭제
        if(!studyScheduleParticipantList.isEmpty()){
            studyScheduleParticipantRepository.deleteAllByScheduleId(scheduleId);
        }
        // 설명. 참가자 새로 추가
        if(!participantList.isEmpty()) {
            for (int p : participantList) {
                StudyScheduleParticipant studyScheduleParticipant = new StudyScheduleParticipant();
                studyScheduleParticipant.setScheduleMemberId(p);
                studyScheduleParticipant.setScheduleId(scheduleId);
                studyScheduleParticipantRepository.save(studyScheduleParticipant);
            }
        }
    }

    @Transactional
    public void removeStudySchedule(int id) {

        StudySchedule studySchedule = studyScheduleRepository.findById(id);
        if(studySchedule != null){
            studySchedule.setUseStatus('N');
            if(!studyScheduleParticipantRepository.findByScheduleId(id).isEmpty()){
                studyScheduleParticipantRepository.deleteAllByScheduleId(id);
            }
        }

    }
}
