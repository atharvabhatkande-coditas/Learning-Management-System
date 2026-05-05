package com.coditas.learningmanagement.controller;

import com.coditas.learningmanagement.dto.AssignmentDto;
import com.coditas.learningmanagement.dto.request.CourseRequest;
import com.coditas.learningmanagement.dto.response.ApplicationResponse;
import com.coditas.learningmanagement.dto.response.CourseResponse;
import com.coditas.learningmanagement.dto.response.GeneralResponse;
import com.coditas.learningmanagement.repository.AssignmentRepository;
import com.coditas.learningmanagement.service.AssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lms/v1/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/{courseId}")
    public ResponseEntity<ApplicationResponse<GeneralResponse>> createAssignment(@Valid @RequestBody AssignmentDto assignmentDto, @PathVariable Long courseId){
        ApplicationResponse<GeneralResponse>applicationResponse=new ApplicationResponse<>(assignmentService.createAssignment(assignmentDto,courseId));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<ApplicationResponse<List<AssignmentDto>>> getAssignment(@PathVariable Long courseId){
        ApplicationResponse<List<AssignmentDto>>applicationResponse=new ApplicationResponse<>(assignmentService.getAssignment(courseId));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{assignmentId}")
    public ResponseEntity<ApplicationResponse<GeneralResponse>> createAssignment(@Valid @RequestBody Map<String,Object> updates , @PathVariable Long assignmentId){
        ApplicationResponse<GeneralResponse>applicationResponse=new ApplicationResponse<>(assignmentService.updateAssignment(assignmentId,updates));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<ApplicationResponse<GeneralResponse>> deleteAssignment(@PathVariable Long assignmentId){
        ApplicationResponse<GeneralResponse>applicationResponse=new ApplicationResponse<>(assignmentService.deleteAssignment(assignmentId));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }


}
