package org.highfives.esc.studylog.controller;

import org.highfives.esc.studylog.dto.StudyclubLogDTO;
import org.highfives.esc.studylog.dto.StudyclubLogInfoDTO;
import org.highfives.esc.studylog.dto.StudyclubLogMemberInfoDTO;
import org.highfives.esc.studylog.service.StudyclubLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studylog")
public class StudyclubLogController {

    private final StudyclubLogService studyclubLogService;

    @Autowired
    public StudyclubLogController(StudyclubLogService studyclubLogService) {
        this.studyclubLogService = studyclubLogService;
    }

    /* 설명. 스터디 로그 생성 기능 */
    @PostMapping("/insert")
    public ResponseEntity<StudyclubLogDTO> insertStudyLog(@RequestBody StudyclubLogDTO studyclubLogDTOData) {

        StudyclubLogDTO studyclubLogDTO = studyclubLogService.insertStudyLog(studyclubLogDTOData);

        return ResponseEntity.ok().body(studyclubLogDTO);
    }

    /* 설명. 스터디 로그 수정 기능 */
    @PutMapping("/update")
    public ResponseEntity<StudyclubLogDTO> updateStudyLog(@RequestBody StudyclubLogDTO studyclubLogDTOData) throws IllegalAccessException {

        StudyclubLogDTO studyclubLogDTO = studyclubLogService.updateStudyclubLog(studyclubLogDTOData);

        return ResponseEntity.ok().body(studyclubLogDTO);

    }

    /* 설명. 스터디 로그 삭제 기능 */
    @DeleteMapping("/delete/{id}")
    public String deleteStudyLog(@PathVariable String id){

        studyclubLogService.deleteStudyLog(id);

        return "ok";
    }

    /* 설명. 스터디 로그 조회 기능 */
    @GetMapping("/find/{id}")
    public ResponseEntity<StudyclubLogDTO> findStudyLogById(@PathVariable String id){

        StudyclubLogDTO studyclubLogDTO = studyclubLogService.findStudyLogById(id);

        return ResponseEntity.ok().body(studyclubLogDTO);
    }

    /* 설명. 스터디 그룹 작성 된 로그 조회 기능 */
    @GetMapping("/find-studyclublog/{studyclub_id}")
    public ResponseEntity<List<StudyclubLogInfoDTO>> findStudyclubLogById(@PathVariable("studyclub_id") String studyclubId){

        List<StudyclubLogInfoDTO> studyclubLogDTO = studyclubLogService.findStudyclubLogById(studyclubId);

        return ResponseEntity.ok().body(studyclubLogDTO);
    }

    /* 설명. 작성한 로그 확인 기능 */
    @GetMapping("/find-writing-studyclublog/{id}")
    public ResponseEntity<List<StudyclubLogMemberInfoDTO>> findWritingStudyclubLogById(@PathVariable("id") String id){

        List<StudyclubLogMemberInfoDTO> studyclubLogDTO = studyclubLogService.findWritingStudyclubLogById(id);

        return ResponseEntity.ok().body(studyclubLogDTO);
    }
}
