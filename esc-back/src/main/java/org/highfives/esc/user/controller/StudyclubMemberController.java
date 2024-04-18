package org.highfives.esc.user.controller;

import org.highfives.esc.user.dao.StudyclubMemberMapper;
import org.highfives.esc.user.dto.StudyclubMemberDTO;
import org.highfives.esc.user.service.StudyclubMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studyclubMember")
public class StudyclubMemberController {
    private final StudyclubMemberService studyclubMemberService;

    @Autowired
    public StudyclubMemberController(StudyclubMemberService studyclubMemberService) {
        this.studyclubMemberService = studyclubMemberService;
    }

    /* 설명. 스터디 클럽 멤버 추가 기능 */
    @PostMapping("/insertMember")
    public ResponseEntity<StudyclubMemberDTO> insertMemberById(@RequestBody StudyclubMemberDTO studyclubMemberDTOData) {

        StudyclubMemberDTO studyclubMemberDTO = studyclubMemberService.insetMemberById(studyclubMemberDTOData);

        return ResponseEntity.ok().body(studyclubMemberDTO);
    }

    /* 설명. 스터디 클럽 멤버 제거 기능 */
    @DeleteMapping("/deleteMember/{memberId}/{studyclubId}")
    public ResponseEntity<StudyclubMemberDTO> deleteByMemberIdAndStudyclubId(@PathVariable("memberId") String memberId,
                                                                             @PathVariable("studyclubId") String studyclubId) {

        studyclubMemberService.deleteByMemberIdAndStudyclubId(memberId, studyclubId);

        return ResponseEntity.ok().build();
    }



}
