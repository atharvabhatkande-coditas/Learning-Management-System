package com.coditas.learningmanagement.controller;

import com.coditas.learningmanagement.dto.response.ApplicationResponse;
import com.coditas.learningmanagement.dto.response.CertificateDto;
import com.coditas.learningmanagement.dto.response.GeneralResponse;
import com.coditas.learningmanagement.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PatchMapping("/{certificateId}")
    public ResponseEntity<ApplicationResponse<GeneralResponse>> updateCertificate(@PathVariable Long certificateId,@RequestBody Map<String,Object> updates){
        ApplicationResponse<GeneralResponse>applicationResponse=new ApplicationResponse<>(certificateService.updateCertificate(certificateId,updates));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }
}
