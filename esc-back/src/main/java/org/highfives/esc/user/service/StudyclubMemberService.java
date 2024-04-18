package org.highfives.esc.user.service;

import org.highfives.esc.user.dto.StudyclubMemberDTO;

import java.util.List;

public interface StudyclubMemberService {




    void deleteByMemberIdAndStudyclubId(StudyclubMemberDTO studyclubMemberData);

    StudyclubMemberDTO insetMemberById(StudyclubMemberDTO studyclubMemberDTOData);
}
