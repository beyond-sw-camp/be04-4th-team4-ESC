package org.highfives.esc.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.highfives.esc.user.dao.UserMapper;
import org.highfives.esc.user.dto.StudyclubInfoDTO;
import org.highfives.esc.user.dto.UserInfoDTO;
import org.highfives.esc.user.dto.UserDTO;
import org.highfives.esc.user.service.UserService;
import org.highfives.esc.user.vo.RegistUser;
import org.highfives.esc.user.vo.ResetPwd;
import org.highfives.esc.user.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private Environment env;

    private ModelMapper modelMapper;

    private UserService userService;

    @Autowired
    public UserController(Environment env, ModelMapper modelMapper, UserService userService) {
        this.env = env;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/regist")
    public ResponseEntity<ResponseUser> registUser(@RequestBody RegistUser userInfo) {
        UserDTO userDTO = modelMapper.map(userInfo, UserDTO.class);
        userService.registUser(userDTO);

        ResponseUser responseUser = modelMapper.map(userDTO, ResponseUser.class);


        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/emailCheck/{email}")
    public String emailCheck(@PathVariable("email") String email) {
        return userService.emailCheck(email);
    }

    @GetMapping("/emailExCheck/{email}")
    public String emailExCheck(@PathVariable("email") String email) {
        return userService.emailExCheck(email);
    }

    @GetMapping("/nicknameCheck/{nickname}")
    public String nicknameCheck(@PathVariable("nickname") String nickname) {
        return userService.nicknameCheck(nickname);
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDTO userDTO = userService.getUserDetailsByEmail(userId);

        ResponseUser returnValue = new ModelMapper().map(userDTO, ResponseUser.class);

        try {
            return ResponseEntity.status(HttpStatus.OK).body(returnValue);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    @GetMapping("/findId")
    public ResponseEntity<String> findUserEmail(@RequestParam("name") String name, @RequestParam("nickname") String nickname) {
        String email = userService.findUserEmail(name, nickname);

        return ResponseEntity.status(HttpStatus.OK).body(email);
    }

    @GetMapping("/checkUser")
    public ResponseEntity<String> checkUserEx(@RequestParam("name") String name, @RequestParam("email") String email) {
        String check = userService.checkUserEx(name, email);

        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPwd resetPwd) {

        String check = userService.resetPassword(resetPwd);

        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    @GetMapping("/findUserList")
    public ResponseEntity<List<UserDTO>> findUserList() {

        List<UserDTO> userDTOList = userService.findUserList();

        return ResponseEntity.ok().body(userDTOList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable String id) {

        UserDTO userDTOList = userService.findUserById(id);

        return ResponseEntity.ok().body(userDTOList);
    }
    /* 설명. 회원 정지 기능 */

    @PutMapping("/ban")
    public ResponseEntity<UserDTO> banUserById(@RequestBody UserDTO banUser) {

        UserDTO userDTO = userService.banUserById(banUser);

        return ResponseEntity.ok().body(userDTO);
    }

    /* 설명. 회원 정보 수정 기능 */

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUserInfoById(@RequestBody UserDTO updateUser) {

        UserDTO userDTO = userService.updateUserInfoById(updateUser);

        return ResponseEntity.ok().body(userDTO);
    }

    /* 설명. 회원이 참여한 스터디 클럽 조회 */

    @GetMapping("/join-studyclub/{member_id}")
    public ResponseEntity<List<StudyclubInfoDTO>> findJoinStudyClubById(@PathVariable("member_id") String memberId) {

        List<StudyclubInfoDTO> studyclubMemberDTO = userService.findJoinStudyClubById(memberId);

        return ResponseEntity.ok().body(studyclubMemberDTO);
    }

    /* 설명. 회원의 포인트 변경 업데이트 */
    @PutMapping("/get-user-point")
    public ResponseEntity<UserDTO> getUserPoint(@RequestBody UserDTO userDTOData) {

        UserDTO userDTO = userService.getUserPoint(userDTOData);

        return ResponseEntity.ok().body(userDTO);
    }

    /* 설명. 회원 탈퇴 기능 */
    @PutMapping("/withdrawal")
    public ResponseEntity<UserDTO> userWithdrawalById(@RequestBody UserDTO userDTOData) {

        UserDTO userDTO = userService.userWithdrawalById(userDTOData);

        return ResponseEntity.ok().body(userDTO);

    }


    /* 설명. 스터디 참여 회원 이름 조회 */
    @GetMapping("/find-join-member-and-name/{studyclub_id}")
    public ResponseEntity<List<UserInfoDTO>> findJoinMemberAndNameById(@PathVariable("studyclub_id") String studyclubId){

        List<UserInfoDTO> userInfoDTO = userService.findJoinMemberAndNameById(studyclubId);

        return ResponseEntity.ok().body(userInfoDTO);
    }
}
