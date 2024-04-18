package org.highfives.esc.user.service;

import org.highfives.esc.user.dto.StudyclubMemberDTO;

import java.util.List;

public interface StudyclubMemberService {




    void deleteByMemberIdAndStudyclubId(String memberId, String studyclubId);

    StudyclubMemberDTO insetMemberById(StudyclubMemberDTO studyclubMemberDTOData);
}
