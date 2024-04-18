package org.highfives.esc.recruit.controller;

import org.highfives.esc.recruit.dto.RecruitPostDTO;
import org.highfives.esc.recruit.service.RecruitService;
import org.highfives.esc.recruit.vo.RecruitPostVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruit")
public class RecruitController {

    private final RecruitService recruitService;

    public RecruitController(RecruitService recruitService) { this.recruitService = recruitService; }

    @GetMapping("/list")
    public ResponseEntity<List<RecruitPostDTO>> findRecruitPosts() {

        List<RecruitPostDTO> recruitPostList = recruitService.findAllRecruitPosts();

        return ResponseEntity.ok().body(recruitPostList);
    }

    @GetMapping("/list/{leaderId}")
    public ResponseEntity<List<RecruitPostDTO>> findRecruitPostByLeaderId(@PathVariable("leaderId") int leaderId) {

        List<RecruitPostDTO> recruitPostList = recruitService.findRecruitPostsByLeaderId(leaderId);

        return ResponseEntity.ok().body(recruitPostList);
    }

    @GetMapping("/detail/{postId}")
    public ResponseEntity<RecruitPostDTO> findRecruitPostById(@PathVariable("postId") int postId) {

        RecruitPostDTO recruitPost = recruitService.findRecruitPostById(postId);

        return ResponseEntity.ok().body(recruitPost);
    }

    // 모집글 작성
    @PostMapping("/regist/{clubId}")
    public ResponseEntity<RecruitPostDTO> registRecruitPost(@RequestBody RecruitPostVO recruitPostVO, @PathVariable("clubId") int clubId) {

        RecruitPostDTO recruitPost = recruitService.registRecruitPost(recruitPostVO, clubId);

        return ResponseEntity.ok().body(recruitPost);
    }

    // 모집글 내용 수정(기존 모집글 내용 조회해서 보여주기)
    @PutMapping("/modify/{recruitId}")
    public ResponseEntity<RecruitPostDTO> modifyRecruitPost(@PathVariable("recruitId") int recruitId, @RequestBody RecruitPostVO recruitPostVO) {

        RecruitPostDTO recruitPost = recruitService.modifyRecruitPost(recruitId, recruitPostVO);

        return ResponseEntity.ok().body(recruitPost);
    }

    // 모집글 삭제 상태로 변경
    @PutMapping("/delete/{recruitId}")
    public ResponseEntity<RecruitPostDTO> deleteRecruitPost(@PathVariable("recruitId") int recruitId) {

        RecruitPostDTO recruitPost = recruitService.deleteRecruitPost(recruitId);

        return ResponseEntity.ok().body(recruitPost);
    }

    @GetMapping("/orderby/{title}")
    public ResponseEntity<RecruitPostDTO> orderByRecruitPost(@PathVariable("title") String title) {

        RecruitPostDTO recruitPost = recruitService.orderByRecruitPost(title);

        return ResponseEntity.ok().body(recruitPost);
    }


}
