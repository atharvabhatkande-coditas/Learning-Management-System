package com.coditas.learningmanagement.controller;

import com.coditas.learningmanagement.dto.response.ApplicationResponse;
import com.coditas.learningmanagement.dto.response.CertificateDto;
import com.coditas.learningmanagement.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lms/v1/certificate")
public class CertificateController {
    private final CertificateService certificateService;

    @GetMapping("/{submissionId}")
    public ResponseEntity<ApplicationResponse<CertificateDto>> generateCertificate(@PathVariable Long submissionId){
        ApplicationResponse<CertificateDto>applicationResponse=new ApplicationResponse<>(certificateService.generateCertificate(submissionId));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }

}
