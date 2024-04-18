package org.highfives.esc.recruit.controller;

import org.highfives.esc.recruit.dto.RecruitApplicationDTO;
import org.highfives.esc.recruit.dto.RecuitApplicationInfoDTO;
import org.highfives.esc.recruit.service.RecruitApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruit-apply")
public class RecruitApplicationController {

    private final RecruitApplicationService recruitApplicationService;

    public RecruitApplicationController(RecruitApplicationService recruitApplicationService) {
        this.recruitApplicationService = recruitApplicationService;
    }

    // 모집글 아이디로 모집글 신청 리스트 조회
    @GetMapping("/post/{recruitPostId}")
    public ResponseEntity<List<RecuitApplicationInfoDTO>> findAllByRecruitId(@PathVariable("recruitPostId") int recruitPostId) {

        List<RecuitApplicationInfoDTO> recruitApplication = recruitApplicationService.findAllByRecruitId(recruitPostId);

        return ResponseEntity.ok().body(recruitApplication);
    }

    // 회원 아이디로 모집글 신청 리스트 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecruitApplicationDTO>> findAllnByUserId(@PathVariable("userId") int userId) {

        List<RecruitApplicationDTO> recruitApplication = recruitApplicationService.findAllByUserId(userId);

        return ResponseEntity.ok().body(recruitApplication);
    }

    // 모집글 신청
    @PostMapping("/regist/{postId}/{userId}")
    public ResponseEntity<RecruitApplicationDTO> registApplication(@PathVariable("userId") int userId, @PathVariable("postId") int postId) {

        RecruitApplicationDTO recruitApplication = recruitApplicationService.registApplication(userId, postId);

        return ResponseEntity.ok().body(recruitApplication);
    }

    @GetMapping("/applyId/{postId}/{userId}")
    public ResponseEntity<RecruitApplicationDTO> findByBothId(@PathVariable("postId") int postId, @PathVariable("userId") int userId) {

        RecruitApplicationDTO recruitApplication = recruitApplicationService.findByBothId(postId, userId);

        return ResponseEntity.ok().body(recruitApplication);
    }

    // 모집글 신청 수락
    @PutMapping("/accept/{applyId}")
    public ResponseEntity<RecruitApplicationDTO> acceptApplication(@PathVariable("applyId") int applyId) {

        RecruitApplicationDTO recruitApplication = recruitApplicationService.acceptApplication(applyId);

        return ResponseEntity.ok().body(recruitApplication);
    }

    // 모집글 신청 거절
    @PutMapping("/reject/{applyId}")
    public ResponseEntity<RecruitApplicationDTO> rejectApplication(@PathVariable("applyId") int applyId) {

        RecruitApplicationDTO recruitApplication = recruitApplicationService.rejectApplication(applyId);

        return ResponseEntity.ok().body(recruitApplication);
    }


    // 모집글 신청 취소
    @DeleteMapping("/delete/{applyId}")
    public void deleteApplication(@PathVariable("applyId") int applyId) {

        recruitApplicationService.deleteApplication(applyId);
    }

}
