package org.highfives.esc.email.controller;

import lombok.RequiredArgsConstructor;
import org.highfives.esc.email.dto.EmailRequestDTO;
import org.highfives.esc.email.service.MailSendService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {
    private final MailSendService mailService;

    @PostMapping("/mailSend")
    public String mailSend(@RequestBody EmailRequestDTO emailDto) {
        System.out.println("이메일 인증 이메일 :" + emailDto.getEmail());
        return mailService.joinEmail(emailDto.getEmail());
    }
}
