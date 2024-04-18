package org.highfives.esc.studyclub.service;

import jakarta.annotation.PostConstruct;
import org.highfives.esc.crawling.service.ExamCrawler;
import org.highfives.esc.crawling.dto.ExamDTO;
import org.highfives.esc.crawling.entity.Exam;
import org.highfives.esc.crawling.repository.ExamRepository;
import org.highfives.esc.studyclub.dto.GoalDTO;
import org.highfives.esc.studyclub.dto.StudyCategoryDTO;
import org.highfives.esc.studyclub.dto.StudyclubDTO;
import org.highfives.esc.studyclub.dto.StudyclubExamDTO;
import org.highfives.esc.studyclub.entity.*;
import org.highfives.esc.studyclub.repository.GoalRepository;
import org.highfives.esc.studyclub.repository.StudyCategoryRepository;
import org.highfives.esc.studyclub.repository.StudyclubExamRepository;
import org.highfives.esc.studyclub.repository.StudyclubRepository;
import org.highfives.esc.studyclub.vo.StudyclubVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudyclubService {

    private final ModelMapper mapper;
    private final StudyclubRepository studyclubRepository;
    private final StudyCategoryRepository studyCategoryRepository;
    private final StudyclubExamRepository studyclubExamRepository;
    private final ExamRepository examRepository;
    private final GoalRepository goalRepository;
    private final ExamCrawler crawler;

    @Autowired
    public StudyclubService(ModelMapper mapper, StudyclubRepository studyclubRepository, StudyCategoryRepository studyCategoryRepository, StudyclubExamRepository studyclubExamRepository, ExamRepository examRepository, GoalRepository goalRepository, ExamCrawler crawler) {
        this.mapper = mapper;
        this.studyclubRepository = studyclubRepository;
        this.studyCategoryRepository = studyCategoryRepository;
        this.studyclubExamRepository = studyclubExamRepository;
        this.examRepository = examRepository;
        this.goalRepository = goalRepository;
        this.crawler = crawler;
    }

    @PostConstruct
    public void init() throws IOException {

        long count = examRepository.count();

        if(count == 0) {
            crawler.CrawlingToeic();
            crawler.CrawlingToeicSpeaking();
            crawler.CrawlingOpic();
        }
    }

    public List<StudyclubDTO> findStudyclubsByLeaderId(int leaderId) {

        List<Studyclub> studyclubList = studyclubRepository.findAll();
        List<StudyclubDTO> studyclubDTOList = new ArrayList<>();

        for (Studyclub studyclub : studyclubList) {
            studyclubDTOList.add(mapper.map(studyclub, StudyclubDTO.class));
        }

        return studyclubDTOList;
    }

    public StudyclubDTO findStudyclubById(int studyclubId) {

        Studyclub studyclub = studyclubRepository.findById(studyclubId).orElseThrow(IllegalArgumentException::new);

        return mapper.map(studyclub, StudyclubDTO.class);
    }

    @Transactional
    public StudyclubDTO registStudyclub(StudyclubVO studyclubVO, int leaderId) {

        Studyclub studyclub = new Studyclub();
        StudyclubExam studyclubExam = new StudyclubExam();

        studyclub.setName(studyclubVO.getName());
        studyclub.setIntroduce(studyclubVO.getIntroduce());
        studyclub.setMemberLimit(studyclubVO.getMemberLimit());

        studyclub.setLeaderId(leaderId);
        studyclub.setDeleteStatus("N");

        studyclubRepository.save(studyclub);

        studyclubExam.setClubId(studyclub.getId());
        studyclubExam.setExamId(studyclubVO.getExamId());
        studyclubExam.setGoalId(studyclubVO.getGoalId());

        studyclubExamRepository.save(studyclubExam);

        return mapper.map(studyclub, StudyclubDTO.class);
    }

    @Transactional
    public StudyclubDTO modifyStudyclub(int studyclubId, StudyclubVO studyclubVO) {

        Studyclub studyclub = studyclubRepository.findById(studyclubId).orElseThrow(IllegalArgumentException::new);

        studyclub.setName(studyclubVO.getName());
        studyclub.setIntroduce(studyclubVO.getIntroduce());
        studyclub.setMemberLimit(studyclubVO.getMemberLimit());

        StudyclubExam studyclubExam = studyclubExamRepository.findByClubId(studyclubId);

        studyclubExam.setExamId(studyclubVO.getExamId());
        studyclubExam.setGoalId(studyclubVO.getGoalId());

        return mapper.map(studyclub, StudyclubDTO.class);
    }

    @Transactional
    public StudyclubDTO deleteStudyclub(int studyclubId) {

        Studyclub studyclub = studyclubRepository.findById(studyclubId).orElseThrow(IllegalArgumentException::new);

        studyclub.setDeleteStatus("Y");

        StudyclubExam studyclubExam = studyclubExamRepository.findByClubId(studyclubId);

        studyclubExamRepository.deleteById(studyclubExam.getId());


        return mapper.map(studyclub, StudyclubDTO.class);
    }

    public StudyCategoryDTO findStudyCategoryById(int categoryId) {

        StudyCategory studyCategory = studyCategoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);

        return mapper.map(studyCategory, StudyCategoryDTO.class);
    }

    public StudyCategoryDTO findStudyCategoryByClubId(int clubId) {

        GoalDTO goal = findGoalByClubId(clubId);
        StudyCategoryDTO studyCategory = findStudyCategoryById(goal.getStudyId());

        return studyCategory;
    }

    public StudyclubExamDTO findStudyclubExamById(int clubId) {

        StudyclubExam studyclubExam = studyclubExamRepository.findByClubId(clubId);

        return mapper.map(studyclubExam, StudyclubExamDTO.class);
    }

    public List<StudyCategoryDTO> findAllStudyCategory() {

        List<StudyCategory> studyCategoryList = studyCategoryRepository.findAll();
        List<StudyCategoryDTO> studyCategoryDTOList = new ArrayList<>();

        for (StudyCategory studyCategory : studyCategoryList) {
            studyCategoryDTOList.add(mapper.map(studyCategory, StudyCategoryDTO.class));
        }

        return studyCategoryDTOList;
    }

    public List<ExamDTO> findExamsByStudyId(int studyId) {

        List<Exam> examList = examRepository.findAllByStudyId(studyId);
        List<ExamDTO> examDTOList = new ArrayList<>();

        for (Exam exam : examList) {
            examDTOList.add(mapper.map(exam, ExamDTO.class));
        }

        return examDTOList;
    }

    public List<GoalDTO> findGoalsByStudyId(int studyId) {

        List<Goal> goalList = goalRepository.findAllByStudyId(studyId);
        List<GoalDTO> goalDTOList = new ArrayList<>();

        for (Goal goal : goalList) {
            goalDTOList.add(mapper.map(goal, GoalDTO.class));
        }

        return goalDTOList;
    }

    public ExamDTO findExamByClubId(int clubId) {

        StudyclubExam studyclubExam = studyclubExamRepository.findByClubId(clubId);

        Exam exam = examRepository.findById(studyclubExam.getExamId()).orElseThrow(IllegalArgumentException::new);

        return mapper.map(exam, ExamDTO.class);
    }


    public GoalDTO findGoalByClubId(int clubId) {

        StudyclubExam studyclubExam = studyclubExamRepository.findByClubId(clubId);

        Goal goal = goalRepository.findById(studyclubExam.getGoalId()).orElseThrow(IllegalArgumentException::new);

        return mapper.map(goal, GoalDTO.class);
    }
}
