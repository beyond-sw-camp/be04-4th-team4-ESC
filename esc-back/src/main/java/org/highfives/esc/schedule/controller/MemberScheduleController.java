package org.highfives.esc.schedule.controller;

import org.highfives.esc.schedule.dto.MemberScheduleDTO;
import org.highfives.esc.schedule.entity.MemberSchedule;
import org.highfives.esc.schedule.service.MemberScheduleService;
import org.highfives.esc.schedule.vo.RequestMemberScheduleListVO;
import org.highfives.esc.schedule.vo.RequestMemberScheduleVO;
import org.highfives.esc.schedule.vo.ResponseMemberScheduleListVO;
import org.highfives.esc.schedule.vo.ResponseMemberScheduleVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.description.ByteCodeElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/member-schedule")
public class MemberScheduleController {

    private final ModelMapper mapper;
    private final MemberScheduleService memberScheduleService;


    @Autowired
    public MemberScheduleController(ModelMapper mapper, MemberScheduleService memberScheduleService) {
        this.mapper = mapper;
        this.memberScheduleService = memberScheduleService;
    }

    /* 설명. 일정 id로 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMemberScheduleVO> findMemberScheduleById(
            @PathVariable("id") int id) {

        MemberScheduleDTO memberScheduleDTO = memberScheduleService.findMemberScheduleById(id);

        ResponseMemberScheduleVO responseMemberScheduleVO = mapper.map(memberScheduleDTO, ResponseMemberScheduleVO.class);

        responseMemberScheduleVO.setMessage("조회성공");
        return ResponseEntity.status(HttpStatus.OK).body(responseMemberScheduleVO);
    }

    /* 설명. studyclub id와 member id로 조회 */
    @GetMapping("member/{studyclubId}/{memberId}")
    public ResponseEntity<ResponseMemberScheduleListVO> findMemberScheduleByMemberId(
            @PathVariable("studyclubId") int studyclubId,
            @PathVariable("memberId") int memberId) {

        ResponseMemberScheduleListVO response = new ResponseMemberScheduleListVO();

        ArrayList<MemberScheduleDTO> memberScheduleDTOList = memberScheduleService.findMemberScheduleByStudyclubIdAndMemberId(studyclubId, memberId);
        ArrayList<ResponseMemberScheduleVO> responseMemberScheduleVOList = new ArrayList<>();

        for (MemberScheduleDTO memberScheduleDTO: memberScheduleDTOList){
            responseMemberScheduleVOList.add(mapper.map(memberScheduleDTO, ResponseMemberScheduleVO.class));
        }

        response.setMessage("조회 성공");
        response.setMemberSchedules(responseMemberScheduleVOList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /* 설명. studyclub id로 조회 */
    @GetMapping("studyclub/{studyclubId}")
    public ResponseEntity<ResponseMemberScheduleListVO> findMemberScheduleByStudyclubId(
            @PathVariable("studyclubId") int studyclubId) {

        ResponseMemberScheduleListVO response = new ResponseMemberScheduleListVO();

        ArrayList<MemberScheduleDTO> memberScheduleDTOList = memberScheduleService.findMemberScheduleByStudyclubId(studyclubId);
        ArrayList<ResponseMemberScheduleVO> responseMemberScheduleVOList = new ArrayList<>();

        for (MemberScheduleDTO memberScheduleDTO: memberScheduleDTOList){
            responseMemberScheduleVOList.add(mapper.map(memberScheduleDTO, ResponseMemberScheduleVO.class));
        }

        response.setMessage("조회 성공");
        response.setMemberSchedules(responseMemberScheduleVOList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /* 설명. studyclub id로 조회해서 겹치는 시간 반환 */
    @GetMapping("overlap/{studyclubId}")
    public ResponseEntity<ResponseMemberScheduleListVO> findOverlapping(
            @PathVariable("studyclubId") int studyclubId) {

        ResponseMemberScheduleListVO response = new ResponseMemberScheduleListVO();

        List<MemberScheduleDTO> memberScheduleDTOList = memberScheduleService.findOverlapMemberSchedulesByStudyclubId(studyclubId);

        // 겹치는 시간을 저장할 리스트
        List<MemberScheduleDTO> overlapSchedules = new ArrayList<>();

        ArrayList<ResponseMemberScheduleVO> responseMemberScheduleVOList = new ArrayList<>();

        for (MemberScheduleDTO memberScheduleDTO: memberScheduleDTOList){
            responseMemberScheduleVOList.add(mapper.map(memberScheduleDTO, ResponseMemberScheduleVO.class));
        }

        response.setMessage("조회 성공");
        response.setMemberSchedules(responseMemberScheduleVOList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    /* 설명. 일정 추가 */
    @PostMapping("save")
    public ResponseEntity<ResponseMemberScheduleVO> saveMemberSchedule(
            @RequestBody RequestMemberScheduleVO requestMemberScheduleVO){
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDatetime = LocalDateTime.parse(requestMemberScheduleVO.getStart(), formatter1);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDatetime = LocalDateTime.parse(requestMemberScheduleVO.getEnd(), formatter2);

        MemberScheduleDTO memberScheduleDTO = new MemberScheduleDTO();
        memberScheduleDTO.setStartDatetime(startDatetime);
        memberScheduleDTO.setEndDatetime(endDatetime);
        memberScheduleDTO.setMemberId(requestMemberScheduleVO.getMemberId());
        memberScheduleDTO.setStudyclubId(requestMemberScheduleVO.getStudyclubId());

        memberScheduleService.saveMemberSchedule(memberScheduleDTO);

        ResponseMemberScheduleVO response = new ResponseMemberScheduleVO();
        response.setMessage("저장 성공");
        response.setStartDatetime(memberScheduleDTO.getStartDatetime());
        response.setEndDatetime(memberScheduleDTO.getEndDatetime());
        response.setMemberId(memberScheduleDTO.getMemberId());
        response.setStudyclubId(memberScheduleDTO.getStudyclubId());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    /* 설명. 일정 수정 */
    @PutMapping("modify")
    public ResponseEntity<ResponseMemberScheduleVO> modifyMemberSchedule(
            @RequestBody RequestMemberScheduleVO requestMemberScheduleVO) {

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDatetime = LocalDateTime.parse(requestMemberScheduleVO.getStart(), formatter1);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDatetime = LocalDateTime.parse(requestMemberScheduleVO.getEnd(), formatter2);

        MemberScheduleDTO memberScheduleDTO = new MemberScheduleDTO();
        memberScheduleDTO.setId(requestMemberScheduleVO.getId());
        memberScheduleDTO.setStartDatetime(startDatetime);
        memberScheduleDTO.setEndDatetime(endDatetime);

        memberScheduleService.modifyMemberSchedule(memberScheduleDTO);

        ResponseMemberScheduleVO response = new ResponseMemberScheduleVO();
        response.setMessage("저장 성공");
        response.setId(memberScheduleDTO.getId());
        response.setStartDatetime(memberScheduleDTO.getStartDatetime());
        response.setEndDatetime(memberScheduleDTO.getEndDatetime());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 설명. 일정 삭제
    @DeleteMapping("remove/{id}")
    public ResponseEntity<ResponseMemberScheduleVO> removeMemberSchedule(
            @PathVariable("id") int id) {

        ResponseMemberScheduleVO response = new ResponseMemberScheduleVO();
        MemberScheduleDTO memberScheduleDTO = memberScheduleService.findMemberScheduleById(id);

        memberScheduleService.removeMemberSchedule(id);

        response.setMessage("삭제 성공");
        response.setId(memberScheduleDTO.getId());
        response.setStartDatetime(memberScheduleDTO.getStartDatetime());
        response.setEndDatetime(memberScheduleDTO.getEndDatetime());
        response.setMemberId(memberScheduleDTO.getMemberId());
        response.setStudyclubId(memberScheduleDTO.getStudyclubId());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
