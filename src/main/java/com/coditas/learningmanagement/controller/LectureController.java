package com.coditas.learningmanagement.controller;

import com.coditas.learningmanagement.dto.LectureDto;
import com.coditas.learningmanagement.dto.response.ApplicationResponse;
import com.coditas.learningmanagement.dto.response.LectureResponse;
import com.coditas.learningmanagement.service.LectureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lms/v1/lecture")
public class LectureController {

    private final LectureService lectureService;

    @PostMapping("/course/{id}")
    public ResponseEntity<ApplicationResponse<LectureResponse>> addNewLecture(@Valid@RequestBody LectureDto lectureDto, @PathVariable Long id){
        ApplicationResponse<LectureResponse>applicationResponse=new ApplicationResponse<>(lectureService.addNewLecture(lectureDto,id));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }
}
