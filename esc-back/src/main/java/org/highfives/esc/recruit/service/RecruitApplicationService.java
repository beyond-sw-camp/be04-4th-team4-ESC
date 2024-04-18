package org.highfives.esc.recruit.service;

import org.highfives.esc.recruit.dto.RecruitApplicationDTO;
import org.highfives.esc.recruit.dto.RecuitApplicationInfoDTO;
import org.highfives.esc.recruit.entity.RecruitApplication;
import org.highfives.esc.recruit.entity.RecruitPost;
import org.highfives.esc.recruit.repository.RecruitApplicationRepository;
import org.highfives.esc.recruit.repository.RecruitPostRepository;
import org.highfives.esc.studyclub.entity.Studyclub;
import org.highfives.esc.studyclub.repository.StudyclubRepository;
import org.highfives.esc.user.dto.StudyclubMemberDTO;
import org.highfives.esc.user.entity.StudyclubMember;
import org.highfives.esc.user.repository.StudyclubMemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecruitApplicationService {

    private final ModelMapper mapper;
    private final RecruitApplicationRepository recruitApplicationRepository;
    private final RecruitPostRepository recruitPostRepository;
    private final StudyclubRepository studyclubRepository;

    private final StudyclubMemberRepository studyclubMemberRepository;

    @Autowired
    public RecruitApplicationService(ModelMapper mapper, RecruitApplicationRepository recruitApplicationRepository, RecruitPostRepository recruitPostRepository, StudyclubRepository studyclubRepository, StudyclubMemberRepository studyclubMemberRepository) {
        this.mapper = mapper;
        this.recruitApplicationRepository = recruitApplicationRepository;
        this.recruitPostRepository = recruitPostRepository;
        this.studyclubRepository = studyclubRepository;
        this.studyclubMemberRepository = studyclubMemberRepository;
    }

    @Transactional(readOnly = true)
    public List<RecuitApplicationInfoDTO> findAllByRecruitId(int recruitPostId) {


    List<RecuitApplicationInfoDTO> recruitApplicationList = recruitApplicationRepository.findAllByRecruitId(recruitPostId);




        return recruitApplicationList;
    }

    @Transactional(readOnly = true)
    public List<RecruitApplicationDTO> findAllByUserId(int userId) {

        List<RecruitApplication> recruitApplicationList = recruitApplicationRepository.findAllByUserId(userId);
        List<RecruitApplicationDTO> recruitApplicationDTOList = new ArrayList<>();

        for (RecruitApplication recruitApplication : recruitApplicationList) {
            recruitApplicationDTOList.add(mapper.map(recruitApplication, RecruitApplicationDTO.class));
        }

        return recruitApplicationDTOList;
    }

    public RecruitApplicationDTO findById(int applyId) {

        RecruitApplication recruitApplication = recruitApplicationRepository.findById(applyId).orElseThrow(IllegalArgumentException::new);

        return mapper.map(recruitApplication, RecruitApplicationDTO.class);
    }

    @Transactional
    public RecruitApplicationDTO registApplication(int userId, int postId) {

        RecruitApplication recruitApplication = new RecruitApplication();

        recruitApplication.setRecruitStatus("대기");
        recruitApplication.setRecruitUserId(userId);
        recruitApplication.setRecruitPostId(postId);

        recruitApplicationRepository.save(recruitApplication);

        return mapper.map(recruitApplication, RecruitApplicationDTO.class);
    }

    @Transactional
    public RecruitApplicationDTO acceptApplication(int applyId) {

        RecruitApplication recruitApplication = recruitApplicationRepository.findById(applyId).orElseThrow(IllegalArgumentException::new);

        recruitApplication.setRecruitStatus("수락");

        RecruitPost recruitPost = recruitPostRepository.findById(recruitApplication.getRecruitPostId()).orElseThrow(IllegalArgumentException::new);
        Studyclub studyclub = studyclubRepository.findById(recruitPost.getClubId()).orElseThrow(IllegalArgumentException::new);

        if(studyclub.getMemberCount() < studyclub.getMemberLimit()) {
            studyclub.setMemberCount(studyclub.getMemberCount() + 1);
        }
        else recruitPost.setRecruitStatus("Y");

        return mapper.map(recruitApplication, RecruitApplicationDTO.class);
    }

    @Transactional
    public RecruitApplicationDTO rejectApplication(int applyId) {

        RecruitApplication recruitApplication = recruitApplicationRepository.findById(applyId).orElseThrow(IllegalArgumentException::new);

        recruitApplication.setRecruitStatus("거절");

        return mapper.map(recruitApplication, RecruitApplicationDTO.class);
    }

    @Transactional
    public void deleteApplication(int applyId) {

        recruitApplicationRepository.deleteById(applyId);
    }

    public RecruitApplicationDTO findByBothId(int postId, int userId) {

        RecruitApplication recruitApplication = recruitApplicationRepository.findByBothId(postId, userId);

        return mapper.map(recruitApplication, RecruitApplicationDTO.class);
    }
}
