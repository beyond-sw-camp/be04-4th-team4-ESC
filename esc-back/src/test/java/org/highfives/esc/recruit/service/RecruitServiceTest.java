package org.highfives.esc.recruit.service;

import org.highfives.esc.recruit.dto.RecruitPostDTO;
import org.highfives.esc.recruit.vo.RecruitPostVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecruitServiceTest {

    @Autowired
    RecruitService recruitService;

    @Test
    @DisplayName("회원 아이디로 작성 모집글 조회")
    void findRecruitPostsByLeaderIdTest() {
        int leaderId = 1;

        List<RecruitPostDTO> recruitPostList = recruitService.findRecruitPostsByLeaderId(leaderId);

        assertNotNull(recruitPostList);
    }

    @Test
    @DisplayName("모집글 상세 조회")
    void findRecruitPostByIdTest() {
        int postId = 1;

        RecruitPostDTO recruitPost = recruitService.findRecruitPostById(postId);

        assertEquals(recruitPost.getId(), postId);
    }

    @Test
    @DisplayName("모집글 등록")
    void registRecruitPostTest() {
        int clubId = 1;

        String title = "제목";
        String content = "내용";

        RecruitPostVO recruitPostVO = new RecruitPostVO(title, content);

        RecruitPostDTO recruitPost = recruitService.registRecruitPost(recruitPostVO, clubId);
        RecruitPostDTO foundRecruitPost = recruitService.findRecruitPostById(recruitPost.getId());

        assertNotNull(foundRecruitPost);
        assertEquals(recruitPost.getTitle(), foundRecruitPost.getTitle());
        assertEquals(recruitPost.getContent(), foundRecruitPost.getContent());
    }

    @Test
    @DisplayName("모집글 수정")
    void modifyRecruitPostTest() {
        int postId = 1;

        String title = "제목";
        String content = "내용";

        RecruitPostVO recruitPostVO = new RecruitPostVO(title, content);

        RecruitPostDTO recruitPost = recruitService.modifyRecruitPost(postId, recruitPostVO);
        RecruitPostDTO foundRecruitPost = recruitService.findRecruitPostById(postId);

        assertNotNull(foundRecruitPost);
        assertEquals(recruitPost.getTitle(), foundRecruitPost.getTitle());
        assertEquals(recruitPost.getContent(), foundRecruitPost.getContent());
    }

    @Test
    @DisplayName("모집글 삭제")
    void deleteRecruitPostTest() {
        int postId = 1;

        recruitService.deleteRecruitPost(postId);

        assertEquals(recruitService.findRecruitPostById(postId).getDeleteStatus(), "Y");
    }
}