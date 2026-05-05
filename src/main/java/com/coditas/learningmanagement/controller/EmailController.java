package com.coditas.learningmanagement.controller;

import com.coditas.learningmanagement.security.EmailDetails;
import com.coditas.learningmanagement.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lms/v1/email")
public class EmailController {
    private final EmailServiceImpl emailService;

    @PostMapping("/sendOtp")
    public String sendOTP(@RequestBody EmailDetails emailDetails){
        return emailService.sendSimpleMail(emailDetails);
    }

    @PostMapping("/sendCode")
    public String sendCode(@RequestBody EmailDetails emailDetails){
        return emailService.sendCode(emailDetails);
    }
}
