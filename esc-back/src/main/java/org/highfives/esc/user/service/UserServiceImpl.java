package org.highfives.esc.user.service;

import org.highfives.esc.user.aggregate.UserEntity;
import org.highfives.esc.user.dto.UserDTO;
import org.highfives.esc.user.repository.UserRepository;
import org.highfives.esc.user.vo.ResetPwd;
import org.modelmapper.ModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.highfives.esc.user.dao.UserMapper;
import org.highfives.esc.user.dto.StudyclubInfoDTO;
import org.highfives.esc.user.dto.UserInfoDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Environment environment;
    private final UserMapper userMapper;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, Environment environment, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.environment = environment;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        System.out.println(users);
        return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void registUser(UserDTO userDTO) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        userRepository.save(userEntity);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null)
            throw new UsernameNotFoundException(email + ": not found");

        return new User(userEntity.getEmail(), userEntity.getPassword(),
                true, true, true, true,
                new ArrayList<>());
    }

    @Override
    public UserDTO getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null)
            throw new UsernameNotFoundException(email + " 아이디의 유저는 존재하지 않음");

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
        System.out.println(userDTO);
        return userDTO;
    }

    @Override
    public String emailCheck(String email) {
        String check;
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            check = "true";
        } else check = "false";

        return check;
    }

    @Override
    public String emailExCheck(String email) {
        String check;
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity.getEmail().equals(email)) {
            check = "true";
        } else check = "false";

        return check;
    }

    @Override
    public String nicknameCheck(String nickname) {
        String check;
        UserEntity userEntity = userRepository.findByNickname(nickname);
        if (userEntity == null) {
            check = "true";
        } else check = "false";

        return check;
    }

    @Override
    public String findUserEmail(String name, String nickname) {
        String email;
        UserEntity userEntity = userRepository.findByNameAndNickname(name, nickname);
        email = userEntity.getEmail();

        return email;
    }

    @Override
    public String checkUserEx(String name, String email) {
        String check;
        UserEntity userEntity = userRepository.findByNameAndEmail(name, email);
        if (userEntity != null) {
            check = "true";
        } else check = "false";

        return check;
    }

    @Override
    public String resetPassword(ResetPwd resetPwd) {
        String check;
        String email = resetPwd.getEmail();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserEntity userEntity = userRepository.findByEmail(email);


        if(encoder.matches(resetPwd.getPassword(), userEntity.getPassword())){
            check = "false";
        }
        else {
            check = "true";
            userEntity.setPassword(bCryptPasswordEncoder.encode(resetPwd.getPassword()));
            userRepository.save(userEntity);
        }

        return check;
    }

    @Override
    @Transactional
    public UserDTO findUserById(String id) {
        UserEntity user = userRepository.findById(Long.valueOf(Integer.valueOf(id))).orElseThrow(IllegalArgumentException::new);

        return userMapper.userToUserDTO(user);
    }

    @Override
    @Transactional
    public List<UserDTO> findUserList() {

        List<UserEntity> userList = userRepository.findAll();
        return userMapper.userListToUserListDTO(userList);
    }

    @Override
    @Transactional
    public UserDTO banUserById(UserDTO banUser) {
        UserEntity userInfo = userRepository.findById((long) banUser.getId()).orElseThrow(IllegalArgumentException::new);

        UserDTO userDTO = UserDTO.builder()
                .id(banUser.getId())
                .name(userInfo.getName())
                .point(userInfo.getPoint())
                .grade(userInfo.getGrade())
                .password(userInfo.getPassword())
                .email(userInfo.getEmail())
                .nickname(userInfo.getNickname())
                .endDate(userInfo.getEndDate())
                .report_count(userInfo.getReportCount())
                .status("Y")
                .build();

        userRepository.save(userMapper.userDTOToUser(userDTO));


        return userDTO;
    }
    @Override
    @Transactional
    public UserDTO updateUserInfoById(UserDTO updateUser) {
        UserDTO userDTO = UserDTO.builder()
                .id(updateUser.getId())
                .name(updateUser.getName())
                .point(updateUser.getPoint())
                .grade(updateUser.getGrade())
                .password(updateUser.getPassword())
                .email(updateUser.getEmail())
                .nickname(updateUser.getNickname())
                .endDate(updateUser.getEndDate())
                .report_count(updateUser.getReportCount())
                .status(updateUser.getStatus())
                .build();

//        log.info("userDTO={}",userDTO);

        userRepository.save(userMapper.userDTOToUser(userDTO));


        return userDTO;
    }


    @Override
    public List<StudyclubInfoDTO> findJoinStudyClubById(String memberId) {

        List<StudyclubInfoDTO> studyclubInfoDTOList = userRepository.findJoinStudyClubById(memberId);




        return studyclubInfoDTOList;
    }



    @Override
    public UserDTO getUserPoint(UserDTO userDTOdata) {
        UserEntity userInfo = userRepository.findById((long) userDTOdata.getId()).orElseThrow(IllegalArgumentException::new);

        UserDTO userDTO = UserDTO.builder()
                .id(userInfo.getId())
                .name(userInfo.getName())
                .grade(userInfo.getGrade())
                .point(userInfo.getPoint() + userDTOdata.getPoint())
                .password(userInfo.getPassword())
                .email(userInfo.getEmail())
                .nickname(userInfo.getNickname())
                .endDate(userInfo.getEndDate())
                .report_count(userInfo.getReportCount())
                .status(userInfo.getStatus())
                .build();

        if(userDTO.getPoint() >= 500)
        {
            int grade = userDTO.getPoint() / 500;
            int exp = userDTO.getPoint() % 500;

            UserDTO userGrade = UserDTO.builder()
                    .id(userDTO.getId())
                    .name(userDTO.getName())
                    .grade(userDTO.getGrade() + grade)
                    .point(exp)
                    .password(userDTO.getPassword())
                    .email(userDTO.getEmail())
                    .nickname(userDTO.getNickname())
                    .endDate(userDTO.getEndDate())
                    .report_count(userDTO.getReportCount())
                    .status(userDTO.getStatus())
                    .build();

            userRepository.save(userMapper.userDTOToUser(userGrade));

            return userGrade;
        }

        userRepository.save(userMapper.userDTOToUser(userDTO));

        return userDTO;
    }

    @Override
    public UserDTO userWithdrawalById(UserDTO userDTOData) {
        UserEntity userInfo = userRepository.findById((long) userDTOData.getId()).orElseThrow(IllegalArgumentException::new);

        UserDTO userDTO = UserDTO.builder()
                .id(userDTOData.getId())
                .name(userInfo.getName())
                .grade(userInfo.getGrade() )
                .point(userInfo.getPoint())
                .password(userInfo.getPassword())
                .email(userInfo.getEmail())
                .nickname(userInfo.getNickname())
                .endDate(userInfo.getEndDate())
                .report_count(userInfo.getReportCount())
                .status("Y")
                .build();
        userRepository.save(userMapper.userDTOToUser(userDTO));

        return userDTO;
    }

    @Override
    public UserDTO signUp(UserDTO userDTOData) {

        UserEntity user = userMapper.userDTOToUser(userDTOData);

        userRepository.save(user);

        return userDTOData;
    }

    @Override
    public List<UserInfoDTO> findJoinMemberAndNameById(String studyclubId) {

        List<UserInfoDTO> userinfoDTO = userRepository.findJoinMemberAndNameById(studyclubId);

        log.info("반환값={}",userinfoDTO);

        return userinfoDTO;
    }
}
