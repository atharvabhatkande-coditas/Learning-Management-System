package com.coditas.learningmanagement.controller;

import com.coditas.learningmanagement.dto.response.ApplicationResponse;
import com.coditas.learningmanagement.dto.response.GeneralResponse;
import com.coditas.learningmanagement.dto.response.ProgressResponse;
import com.coditas.learningmanagement.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lms/v1/enroll")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping("/course/{courseId}")
    public ResponseEntity<ApplicationResponse<GeneralResponse>>enrollIntoCourse(@PathVariable Long courseId){
        ApplicationResponse<GeneralResponse>applicationResponse=new ApplicationResponse<>(enrollmentService.enrollIntoCourse(courseId));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }

    @GetMapping("/progress/{enrollmentId}")
    public ResponseEntity<ApplicationResponse<ProgressResponse>>getProgress(@PathVariable Long enrollmentId){
        ApplicationResponse<ProgressResponse>applicationResponse=new ApplicationResponse<>(enrollmentService.getProgress(enrollmentId));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }


}
