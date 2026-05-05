package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.security.EmailDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleMail(EmailDetails emailDetails) {

        try{
            Random r=new Random();
            int min=100000;
            int max=999999;

            int randomNumber= r.nextInt(max-min+1)+min;

            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(Integer.toString(randomNumber));
            mailMessage.setSubject("Otp For Verification");
            javaMailSender.send(mailMessage);

            return "OTP Sent Successfully";

        }catch (Exception e){
            return "Error Sending OTP";
        }
    }

    @Override
    public String sendCode(EmailDetails emailDetails) {
        try{

            UUID uuid=UUID.randomUUID();

            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(uuid.toString());
            mailMessage.setSubject("Unique Id For Verification");
            javaMailSender.send(mailMessage);

            return "OTP Sent Successfully";

        }catch (Exception e){
            return "Error Sending OTP";
        }
    }
}
