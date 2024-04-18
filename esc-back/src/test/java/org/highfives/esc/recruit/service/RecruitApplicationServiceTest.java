package org.highfives.esc.recruit.service;

import org.highfives.esc.recruit.dto.RecruitApplicationDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecruitApplicationServiceTest {

    @Autowired
    RecruitApplicationService recruitApplicationService;

    @Test
    @DisplayName("신청")
    void registApplicationTest() {
        int userId = 1;
        int postId = 1;

        RecruitApplicationDTO recruitApplication = recruitApplicationService.registApplication(userId, postId);

        assertEquals(recruitApplication.getRecruitUserId(), userId);
        assertEquals(recruitApplication.getRecruitPostId(), postId);
    }

    @Test
    @DisplayName("신청 수락")
    void acceptApplicationTest() {
        int applyId = 1;

        RecruitApplicationDTO recruitApplication = recruitApplicationService.acceptApplication(applyId);

        assertEquals(recruitApplication.getRecruitStatus(), "수락");
    }

    @Test
    @DisplayName("신청 거절")
    void rejectApplicationTest() {
        int applyId = 1;

        RecruitApplicationDTO recruitApplication = recruitApplicationService.rejectApplication(applyId);

        assertEquals(recruitApplication.getRecruitStatus(), "거절");
    }

    @Test
    void deleteApplicationTest() {
        int applyId = 1;

        recruitApplicationService.deleteApplication(applyId);

        assertThrows(IllegalArgumentException.class, () -> {
            recruitApplicationService.findById(applyId);
        });
    }
}