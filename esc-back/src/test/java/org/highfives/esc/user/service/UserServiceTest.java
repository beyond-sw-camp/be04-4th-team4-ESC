package org.highfives.esc.user.service;

import org.highfives.esc.user.dto.UserDTO;
import org.highfives.esc.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {
    private UserServiceImpl userService;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceTest(UserServiceImpl userService, UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Test
    @Transactional
    void 회원가입_테스트() {
        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setName("테스트");
        userDTO.setNickname("테스트 닉네임");
        userDTO.setPassword("1234");
        userDTO.setEmail("abcd@naver.com");

        //when
        userService.registUser(userDTO);

        //then
        String findemail = userRepository.findByEmail("abcd@naver.com").getEmail();
        assertThat(userDTO.getEmail()).isEqualTo(findemail);
    }

    @Test
    void 이메일_중복체크_이메일이_없을때() {
        //given
        String email = "baek0810@naver.com";

        //when
        String check = userService.emailCheck(email);

        //then
        assertThat(check).isEqualTo("true");
    }

    @Test
    void 이메일_중복체크_이메일이_존재할때() {
        //given
        String email = "kangtw@gmail.com";

        //when
        String check = userService.emailExCheck(email);

        //then
        assertThat(check).isEqualTo("true");
    }

    @Test
    void 닉네임_중복체크() {
        //given
        String nickname = "없는닉네임";

        //when
        String check = userService.nicknameCheck(nickname);

        //then
        assertThat(check).isEqualTo("true");
    }

    @Test
    void 이름과_닉네임으로_이메일찾기() {
        //given
        String name = "강태우";
        String nickname = "kangtw";

        //when
        String email = userService.findUserEmail(name, nickname);

        //then
        assertThat(email).isEqualTo("kangtw@gmail.com");
    }

    @Test
    void 회원_중복_체크() {
        //given
        String name = "없는 이름";
        String email = "없는 이메일";

        //when
        String check = userService.checkUserEx(name,email);

        //then
        assertThat(check).isEqualTo("false");
    }
}
