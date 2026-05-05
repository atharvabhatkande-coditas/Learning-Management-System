package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.security.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails emailDetails);
    String sendCode(EmailDetails emailDetails);
}
