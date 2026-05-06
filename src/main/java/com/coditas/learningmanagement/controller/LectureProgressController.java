package com.coditas.learningmanagement.controller;

import com.coditas.learningmanagement.dto.response.ApplicationResponse;
import com.coditas.learningmanagement.dto.response.SingleResponse;
import com.coditas.learningmanagement.service.LectureProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lms/v1/progress")
public class LectureProgressController {

    private final LectureProgressService lectureProgressService;

    @PostMapping("/start/{lectureId}")
    public ResponseEntity<ApplicationResponse<SingleResponse>>startLecture(@PathVariable Long lectureId){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(lectureProgressService.startLecture(lectureId));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{lectureId}")
    public ResponseEntity<ApplicationResponse<SingleResponse>>completeLecture(@PathVariable Long lectureId){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(lectureProgressService.completeLecture(lectureId));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }

}
