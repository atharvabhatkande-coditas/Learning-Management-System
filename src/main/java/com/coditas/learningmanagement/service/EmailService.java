package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.dto.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails emailDetails);
    String sendCode(EmailDetails emailDetails);
}
