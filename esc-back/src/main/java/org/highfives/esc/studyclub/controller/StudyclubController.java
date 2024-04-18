package org.highfives.esc.studyclub.controller;

import org.highfives.esc.crawling.dto.ExamDTO;
import org.highfives.esc.studyclub.dto.GoalDTO;
import org.highfives.esc.studyclub.dto.StudyCategoryDTO;
import org.highfives.esc.studyclub.dto.StudyclubDTO;
import org.highfives.esc.studyclub.service.StudyclubService;
import org.highfives.esc.studyclub.vo.StudyclubVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studyclub")
public class StudyclubController {

    private final StudyclubService studyclubService;

    public StudyclubController(StudyclubService studyclubService) {
        this.studyclubService = studyclubService;
    }

    // 리더(회원) 아이디로 스터디클럽 리스트 조회
    @GetMapping("/list/{leaderId}")
    public ResponseEntity<List<StudyclubDTO>> findStudyclubsByLeaderId(@PathVariable("leaderId") int leaderId) {

        List<StudyclubDTO> studyclubList = studyclubService.findStudyclubsByLeaderId(leaderId);

        return ResponseEntity.ok().body(studyclubList);
    }

    // 스터디클럽 아이디로 스터디클럽 상세 정보 조회
    @GetMapping("/detail/{studyclubId}")
    public ResponseEntity<StudyclubDTO> findStudyclubById(@PathVariable("studyclubId") int studyclubId) {

        StudyclubDTO studyclub = studyclubService.findStudyclubById(studyclubId);

        return ResponseEntity.ok().body(studyclub);
    }

    // 스터디클럽 신규 등록
    @PostMapping("/regist/{leaderId}")
    public ResponseEntity<StudyclubDTO> registStudyClub(@RequestBody StudyclubVO studyclubVO, @PathVariable("leaderId") int leaderId) {

        StudyclubDTO studyclub = studyclubService.registStudyclub(studyclubVO, leaderId);

        return ResponseEntity.ok().body(studyclub);
    }

    // 스터디클럽 내용 수정(기존 스터디클럽 내용 조회해서 보여주기)
    @PutMapping("/modify/{studyclubId}")
    public ResponseEntity<StudyclubDTO> modifyStudyclub(@PathVariable("studyclubId") int studyclubId, @RequestBody StudyclubVO studyclubVO) {

        StudyclubDTO studyclub = studyclubService.modifyStudyclub(studyclubId, studyclubVO);

        return ResponseEntity.ok().body(studyclub);
    }

    // 스터디클럽 삭제 상태로 변경
    @PutMapping("/delete/{studyclubId}")
    public ResponseEntity<StudyclubDTO> deleteStudyclub(@PathVariable("studyclubId") int studyclubId) {

        StudyclubDTO studyclub = studyclubService.deleteStudyclub(studyclubId);

        return ResponseEntity.ok().body(studyclub);
    }

    @GetMapping("/category")
    public ResponseEntity<List<StudyCategoryDTO>> findAllStudyCategoryById() {

        List<StudyCategoryDTO> studyCategoryList = studyclubService.findAllStudyCategory();

        return ResponseEntity.ok().body(studyCategoryList);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<StudyCategoryDTO> findStudyCategoryById(@PathVariable("categoryId") int categoryId) {

        StudyCategoryDTO studyCategory= studyclubService.findStudyCategoryById(categoryId);

        return ResponseEntity.ok().body(studyCategory);
    }

    @GetMapping("/study-category/{clubId}")
    public ResponseEntity<StudyCategoryDTO> findStudyCategoryByClubId(@PathVariable("clubId") int clubId) {

        StudyCategoryDTO studyCategory= studyclubService.findStudyCategoryByClubId(clubId);

        return ResponseEntity.ok().body(studyCategory);
    }

    // 스터디 카테고리별 시험일 목록 조회
    @GetMapping("/exam/{studyId}")
    public ResponseEntity<List<ExamDTO>> findExamsByStudyId(@PathVariable("studyId") int studyId) {

        List<ExamDTO> examList = studyclubService.findExamsByStudyId(studyId);

        return ResponseEntity.ok().body(examList);
    }

    // 스터디 카테고리별 목표 목록 조회
    @GetMapping("/goal/{studyId}")
    public ResponseEntity<List<GoalDTO>> findGoalsByStudyId(@PathVariable("studyId") int studyId) {

        List<GoalDTO> goalList = studyclubService.findGoalsByStudyId(studyId);

        return ResponseEntity.ok().body(goalList);
    }

    @GetMapping("/study-exam/{clubId}")
    public ResponseEntity<ExamDTO> findExamByClubId(@PathVariable("clubId") int clubId) {

        ExamDTO exam = studyclubService.findExamByClubId(clubId);

        return ResponseEntity.ok().body(exam);
    }

    @GetMapping("/study-goal/{clubId}")
    public ResponseEntity<GoalDTO> findGoalByClubId(@PathVariable("clubId") int clubId) {

        GoalDTO goal = studyclubService.findGoalByClubId(clubId);

        return ResponseEntity.ok().body(goal);
    }
}
