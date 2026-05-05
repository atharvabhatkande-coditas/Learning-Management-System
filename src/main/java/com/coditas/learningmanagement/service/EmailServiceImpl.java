package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.entity.Otp;
import com.coditas.learningmanagement.entity.UniqueCode;
import com.coditas.learningmanagement.repository.OtpRepository;
import com.coditas.learningmanagement.repository.UniqueCodeRepository;
import com.coditas.learningmanagement.security.EmailDetails;
import io.micrometer.observation.ObservationTextPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
    private final JavaMailSender javaMailSender;
    private final OtpRepository otpRepository;
    private final UniqueCodeRepository uniqueCodeRepository;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleMail(EmailDetails emailDetails) {

        try{
            Random r=new Random();
            int min=100000;
            int max=999999;

            int randomNumber= r.nextInt(max-min+1)+min;
            String otpValue=Integer.toString(randomNumber);
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText("Your Otp For registration: "+otpValue);
            mailMessage.setSubject("Otp For Verification");
            javaMailSender.send(mailMessage);

            Otp existingOtp=otpRepository.findByEmail(emailDetails.getRecipient()).orElse(null);
            if(existingOtp!=null){
                existingOtp.setOtpValue(otpValue);
                existingOtp.setExpireAt(new Date(System.currentTimeMillis()+1000*60*5));
                otpRepository.save(existingOtp);
                return "OTP Sent Successfully";
            }
            Otp otp=new Otp();
            otp.setEmail(emailDetails.getRecipient());
            otp.setOtpValue(otpValue);
            otp.setExpireAt(new Date(System.currentTimeMillis()+1000*60*5));
            otpRepository.save(otp);


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
            mailMessage.setText("Your Unique Id for verification: "+uuid);
            mailMessage.setSubject("Unique Id For Verification");
            javaMailSender.send(mailMessage);
            UniqueCode existingCode=uniqueCodeRepository.findByEmail(emailDetails.getRecipient()).orElse(null);
            if(existingCode!=null){
                existingCode.setExpireAt(new Date(System.currentTimeMillis()+1000*60*5));
                existingCode.setCode(uuid.toString());
            }

            UniqueCode uniqueCode=new UniqueCode();
            uniqueCode.setCode(uuid.toString());
            uniqueCode.setEmail(emailDetails.getRecipient());
            uniqueCode.setExpireAt(new Date(System.currentTimeMillis()+1000*60*5));
            uniqueCodeRepository.save(uniqueCode);
            return "UniqueCode Sent Successfully";

        }catch (Exception e){
            return "Error Sending UniqueCode";
        }
    }
}
