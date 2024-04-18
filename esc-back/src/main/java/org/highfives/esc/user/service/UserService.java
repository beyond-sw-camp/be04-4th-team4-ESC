package org.highfives.esc.user.service;


import org.highfives.esc.user.dto.UserDTO;
import org.highfives.esc.user.vo.ResetPwd;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.highfives.esc.user.dto.StudyclubInfoDTO;
import org.highfives.esc.user.dto.UserInfoDTO;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDTO> getAllUsers();

    void registUser(UserDTO userDTO);

    UserDTO getUserDetailsByEmail(String email);

    String emailCheck(String email);

    String nicknameCheck(String nickname);

    String findUserEmail(String name, String nickname);

    String emailExCheck(String email);

    String checkUserEx(String name, String email);

    String resetPassword(ResetPwd resetPwd);

    UserDTO findUserById(String id);

    List<UserDTO> findUserList();

//    List<UserDTO> findStudyClubById(String id);

    UserDTO banUserById(UserDTO banUser);

    UserDTO updateUserInfoById(UserDTO updateUser);

    UserDTO getUserPoint(UserDTO userDTOdata);

    UserDTO userWithdrawalById(UserDTO userDTOData);

    UserDTO signUp(UserDTO userDTOData);

    List<UserInfoDTO> findJoinMemberAndNameById(String studyclubId);

    List<StudyclubInfoDTO> findJoinStudyClubById(String memberId);
}
