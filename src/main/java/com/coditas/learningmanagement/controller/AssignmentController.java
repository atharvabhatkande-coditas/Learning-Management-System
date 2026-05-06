package com.coditas.learningmanagement.controller;

import com.coditas.learningmanagement.dto.AssignmentDto;
import com.coditas.learningmanagement.dto.request.AssignmentUpdateRequest;
import com.coditas.learningmanagement.dto.response.ApplicationResponse;
import com.coditas.learningmanagement.dto.response.SingleResponse;
import com.coditas.learningmanagement.service.AssignmentService;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lms/v1/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/{courseId}")
    public ResponseEntity<ApplicationResponse<SingleResponse>> createAssignment(@Valid @RequestBody AssignmentDto assignmentDto, @PathVariable Long courseId){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(assignmentService.createAssignment(assignmentDto,courseId));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<ApplicationResponse<List<AssignmentDto>>> getAssignment(@PathVariable Long courseId){
        ApplicationResponse<List<AssignmentDto>>applicationResponse=new ApplicationResponse<>(assignmentService.getAssignment(courseId));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }

    @PatchMapping("/{assignmentId}")
    public ResponseEntity<ApplicationResponse<SingleResponse>> updateAssignment(@Valid @RequestBody AssignmentUpdateRequest updates , @PathVariable Long assignmentId) throws JsonMappingException {
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(assignmentService.updateAssignment(assignmentId,updates));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<ApplicationResponse<SingleResponse>> deleteAssignment(@PathVariable Long assignmentId){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(assignmentService.deleteAssignment(assignmentId));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }


}
