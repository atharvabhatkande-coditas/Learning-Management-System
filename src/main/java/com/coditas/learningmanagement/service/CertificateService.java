package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.dto.response.CertificateDto;
import com.coditas.learningmanagement.dto.response.SingleResponse;
import com.coditas.learningmanagement.entity.*;
import com.coditas.learningmanagement.enums.SubmissionStatus;
import com.coditas.learningmanagement.exception.ForbiddenException;
import com.coditas.learningmanagement.exception.NotFoundException;
import com.coditas.learningmanagement.mappers.CertificateMapper;
import com.coditas.learningmanagement.repository.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

import static com.coditas.learningmanagement.constants.AssignmentConstants.*;
import static com.coditas.learningmanagement.constants.DtoConstants.UPDATED;
import static com.coditas.learningmanagement.constants.ExceptionConstants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final CustomUserDetailsRepository customUserDetailsRepository;
    private final AuthService authService;
    private final AssignmentSubmissionRepository assignmentSubmissionRepository;
    private final CertificateMapper certificateMapper;
    private final ObjectMapper objectMapper;


    public CertificateDto generateCertificate(Long submissionId) {
        Employee issuedTo=customUserDetailsRepository.findByUsername(authService.getUserDetails().getUsername())
                .orElseThrow(()->new NotFoundException(USER_NOT_FOUND));
        AssignmentSubmission assignmentSubmission=assignmentSubmissionRepository.findById(submissionId)
                .orElseThrow(()->new NotFoundException(ASSIGNMENT_NOT_SUBMITTED));
        if(!assignmentSubmission.getSubmissionStatus().equals(SubmissionStatus.REVIEWED) && assignmentSubmission.getScore()<60){
            throw new ForbiddenException(ASSIGNMENT_NOT_REVIEWED);
        }
        Certificate certificate=new Certificate();
        certificate.setCourse(assignmentSubmission.getAssignment().getCourse());
        certificate.setIssuedAt(LocalDate.now());
        certificate.setIssuedTo(issuedTo);
        certificateRepository.save(certificate);

        return certificateMapper.toDto(certificate);

    }

}
