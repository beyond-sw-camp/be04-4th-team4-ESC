package org.highfives.esc.schedule.service;


import org.highfives.esc.schedule.dto.MemberScheduleDTO;

import org.highfives.esc.schedule.entity.MemberSchedule;
import org.highfives.esc.schedule.repository.MemberScheduleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MemberScheduleService {

    private final ModelMapper mapper;
    private final MemberScheduleRepository memberScheduleRepository;

    @Autowired
    public MemberScheduleService(ModelMapper mapper, MemberScheduleRepository memberScheduleRepository) {
        this.mapper = mapper;
        this.memberScheduleRepository = memberScheduleRepository;
    }

    public MemberScheduleDTO findMemberScheduleById(int id) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        MemberSchedule memberSchedule = memberScheduleRepository.findById(id);
        if (memberSchedule != null) {
            MemberScheduleDTO memberScheduleDTO = mapper.map(memberSchedule, MemberScheduleDTO.class);
            System.out.println(memberScheduleDTO);

            return memberScheduleDTO;
        } else {
            return null;
        }
    }

    public ArrayList<MemberScheduleDTO> findMemberScheduleByStudyclubIdAndMemberId(int studyclubId, int memberId) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ArrayList<MemberSchedule> memberScheduleList = memberScheduleRepository.findByStudyclubIdAndMemberId(studyclubId, memberId);
        ArrayList<MemberScheduleDTO> memberScheduleDTOList = new ArrayList<>();

        for (MemberSchedule memberSchedule: memberScheduleList) {
            memberScheduleDTOList.add(mapper.map(memberSchedule, MemberScheduleDTO.class));
        }

        return memberScheduleDTOList;
    }

    public ArrayList<MemberScheduleDTO> findMemberScheduleByStudyclubId(int studyclueId) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ArrayList<MemberSchedule> memberScheduleList = memberScheduleRepository.findByStudyclubId(studyclueId);
        ArrayList<MemberScheduleDTO> memberScheduleDTOList = new ArrayList<>();

        for (MemberSchedule memberSchedule: memberScheduleList) {
            memberScheduleDTOList.add(mapper.map(memberSchedule, MemberScheduleDTO.class));
        }

        return memberScheduleDTOList;
    }

    public List<MemberScheduleDTO> findOverlapMemberSchedulesByStudyclubId(int studyclubId) {
        List<MemberSchedule> memberScheduleList = memberScheduleRepository.findByStudyclubId(studyclubId);
        List<MemberScheduleDTO> overlapSchedules = new ArrayList<>();

        if (memberScheduleList.size() < 2) {
            return overlapSchedules; // 일정 2개 미만이면 겹치는 시간 범위가 없음
        }

        // 스터디 멤버 조회로 변경 필요
        ArrayList<Integer> members = new ArrayList<>();

        // 멤버 수 저장, 추후 교체
        for (MemberSchedule memberSchedule: memberScheduleList) {
            int memberId = memberSchedule.getMemberId();
            if(!members.contains(memberId)){
                members.add(memberId);
            }
        }

        int num = members.size();

        // 시간을 30분 단위로 쪼개어 각 시간대의 겹치는 멤버 수를 저장
        Map<LocalDateTime, Integer> overlapCounts = new HashMap<>();

        // 모든 멤버의 일정을 하나씩 확인하면서 겹치는 시간대의 멤버 수를 계산
        for (MemberSchedule schedule : memberScheduleList) {
            LocalDateTime start = schedule.getStartDatetime();
            LocalDateTime end = schedule.getEndDatetime();

            // 해당 일정의 시간대를 30분 단위로 쪼갬
            LocalDateTime current = start;
            while (current.isBefore(end)) {
                overlapCounts.put(current, overlapCounts.getOrDefault(current, 0) + 1);
                current = current.plusMinutes(30); // 30분씩 증가
            }
        }

        // 겹치는 시간대의 멤버 수가 최대인 경우를 찾아서 결과에 추가
        for (Map.Entry<LocalDateTime, Integer> entry : overlapCounts.entrySet()) {
            if (entry.getValue() == num) {
                MemberScheduleDTO overlapSchedule = new MemberScheduleDTO();
                overlapSchedule.setStartDatetime(entry.getKey());
                overlapSchedule.setEndDatetime(entry.getKey().plusMinutes(30)); // 30분 후까지의 시간대
                overlapSchedules.add(overlapSchedule);
            }
        }

        return overlapSchedules;
    }

    @Transactional
    public void saveMemberSchedule(MemberScheduleDTO memberScheduleDTO) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        MemberSchedule memberSchedule = new MemberSchedule();
        memberSchedule.setStartDatetime(memberScheduleDTO.getStartDatetime());
        memberSchedule.setEndDatetime(memberScheduleDTO.getEndDatetime());
        memberSchedule.setMemberId(memberScheduleDTO.getMemberId());
        memberSchedule.setStudyclubId(memberScheduleDTO.getStudyclubId());

        memberScheduleRepository.save(memberSchedule);
    }

    @Transactional
    public void modifyMemberSchedule(MemberScheduleDTO memberScheduleDTO) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // 설명. id로 변경할 일정 조회
        MemberSchedule memberSchedule = memberScheduleRepository.findById(memberScheduleDTO.getId());

        // 설명. 일정 시간 변경
        memberSchedule.setStartDatetime(memberScheduleDTO.getStartDatetime());
        memberSchedule.setEndDatetime(memberScheduleDTO.getEndDatetime());
    }

    @Transactional
    public void removeMemberSchedule(int id) {

        memberScheduleRepository.deleteById(id);
    }
}
