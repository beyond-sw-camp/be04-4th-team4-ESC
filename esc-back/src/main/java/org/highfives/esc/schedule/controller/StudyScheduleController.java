package org.highfives.esc.schedule.controller;

import org.highfives.esc.schedule.dto.StudyScheduleDTO;
import org.highfives.esc.schedule.service.StudyScheduleService;
import org.highfives.esc.schedule.vo.RequestScheduleVO;
import org.highfives.esc.schedule.vo.ResponseScheduleVO;
import org.highfives.esc.schedule.vo.ResponseStudyScheduleListVO;
import org.highfives.esc.schedule.vo.ResponseStudyScheduleVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RestController
@RequestMapping("/study-schedule")
public class StudyScheduleController {

    private final ModelMapper mapper;
    private final StudyScheduleService studyScheduleService;

    @Autowired
    public StudyScheduleController(ModelMapper mapper, StudyScheduleService studyScheduleService) {
        this.mapper = mapper;
        this.studyScheduleService = studyScheduleService;
    }

    @GetMapping("/schedule/{id}")
    public ResponseEntity<ResponseScheduleVO> findStudyScheduleById(
            @PathVariable("id") int id) {

        ResponseScheduleVO response = studyScheduleService.findStudyScheduleById(id);
        if(response != null){
            response.setMessage("조회 성공");
        } else {
            response.setMessage("조회 실패");
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/studyclub/{studyclubId}")
    public ResponseEntity<ResponseStudyScheduleListVO> findStudyScheduleByStudyclubId(
            @PathVariable("studyclubId") int studyculbId) {
        ResponseStudyScheduleListVO response = new ResponseStudyScheduleListVO();

        ArrayList<StudyScheduleDTO> studyScheduleDTOList = studyScheduleService.findStudyScheduleByStudyId(studyculbId);
        ArrayList<ResponseStudyScheduleVO> studyScheduleVOList = new ArrayList<>();
        if(studyScheduleDTOList != null){

            for(StudyScheduleDTO studyScheduleDTO: studyScheduleDTOList){
                studyScheduleVOList.add(mapper.map(studyScheduleDTO, ResponseStudyScheduleVO.class));
            }

            response.setMessage("조회 성공");
            response.setStudySchedules(studyScheduleVOList);
        } else {
            response.setMessage("조회 실패");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseScheduleVO> saveStudySchedule(
            @RequestBody RequestScheduleVO requestScheduleVO) {

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDatetime = LocalDateTime.parse(requestScheduleVO.getStart(), formatter1);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDatetime = LocalDateTime.parse(requestScheduleVO.getEnd(), formatter2);

        String useStatusStr = String.valueOf(requestScheduleVO.getUseStatus());
        char useStatus = useStatusStr.charAt(0);

        StudyScheduleDTO studyScheduleDTO = new StudyScheduleDTO();

        studyScheduleDTO.setTitle(requestScheduleVO.getTitle());
        studyScheduleDTO.setContent(requestScheduleVO.getContent());
        studyScheduleDTO.setStartDatetime(startDatetime);
        studyScheduleDTO.setEndDatetime(endDatetime);
        studyScheduleDTO.setUseStatus(useStatus);
        studyScheduleDTO.setWriterId(requestScheduleVO.getWriterId());
        studyScheduleDTO.setStudyclubId(requestScheduleVO.getStudyclubId());

        ArrayList<Integer> participantList = requestScheduleVO.getParticipantList();

        studyScheduleService.saveStudySchedule(studyScheduleDTO, participantList);

        ResponseScheduleVO response = new ResponseScheduleVO();
        response.setMessage("추가 성공");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 설명. 스터디 일정 수정
    @PutMapping("/modify")
    public ResponseEntity<ResponseScheduleVO> modifyStudySchedule(
            @RequestBody RequestScheduleVO requestScheduleVO) {
        System.out.println(requestScheduleVO);

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDatetime = LocalDateTime.parse(requestScheduleVO.getStart(), formatter1);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDatetime = LocalDateTime.parse(requestScheduleVO.getEnd(), formatter2);

        StudyScheduleDTO studyScheduleDTO = new StudyScheduleDTO();

        studyScheduleDTO.setId(requestScheduleVO.getId());
        studyScheduleDTO.setTitle(requestScheduleVO.getTitle());
        studyScheduleDTO.setContent(requestScheduleVO.getContent());
        studyScheduleDTO.setStartDatetime(startDatetime);
        studyScheduleDTO.setEndDatetime(endDatetime);

        ArrayList<Integer> participantList = requestScheduleVO.getParticipantList();
        studyScheduleService.modifyStudySchedule(studyScheduleDTO, participantList);

        ResponseScheduleVO response = new ResponseScheduleVO();
        response.setMessage("수정 성공");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 설명. 스터디 일정 삭제
    @PutMapping("/remove/{id}")
    public ResponseEntity<ResponseScheduleVO> removeStudySchedule(@PathVariable("id") int id){
        studyScheduleService.removeStudySchedule(id);

        ResponseScheduleVO response = new ResponseScheduleVO();
        response.setMessage("삭제 성공");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
