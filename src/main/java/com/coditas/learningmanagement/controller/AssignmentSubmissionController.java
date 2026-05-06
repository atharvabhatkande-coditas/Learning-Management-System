package com.coditas.learningmanagement.controller;

import com.coditas.learningmanagement.dto.request.GeneralRequest;
import com.coditas.learningmanagement.dto.request.ScoresDto;
import com.coditas.learningmanagement.dto.response.ApplicationResponse;
import com.coditas.learningmanagement.dto.response.AssignmentSubmissionDto;
import com.coditas.learningmanagement.dto.response.GeneralResponse;
import com.coditas.learningmanagement.service.AssignmentSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lms/v1/submission")
public class AssignmentSubmissionController {
    private final AssignmentSubmissionService assignmentSubmissionService;


    @PostMapping("/{assignmentId}")
    public ResponseEntity<ApplicationResponse<GeneralResponse>>submitAssignment(@PathVariable Long assignmentId, @RequestBody GeneralRequest request){
        ApplicationResponse<GeneralResponse>applicationResponse=new ApplicationResponse<>(assignmentSubmissionService.submitAssignment(assignmentId,request));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<ApplicationResponse<List<AssignmentSubmissionDto>>>getSubmissions(@PathVariable Long assignmentId){
        ApplicationResponse<List<AssignmentSubmissionDto>>applicationResponse=new ApplicationResponse<>(assignmentSubmissionService.getSubmissions(assignmentId));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }

    @PatchMapping("scores/{submissionId}")
    public ResponseEntity<ApplicationResponse<GeneralResponse>>updateScores(@PathVariable Long submissionId,@RequestBody ScoresDto scores){
        ApplicationResponse<GeneralResponse>applicationResponse=new ApplicationResponse<>(assignmentSubmissionService.updateScores(submissionId,scores));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }


}
