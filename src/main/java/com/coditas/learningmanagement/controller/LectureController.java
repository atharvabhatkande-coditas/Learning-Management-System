package com.coditas.learningmanagement.controller;

import com.coditas.learningmanagement.dto.LectureDto;
import com.coditas.learningmanagement.dto.request.LectureUpdateRequest;
import com.coditas.learningmanagement.dto.response.ApplicationResponse;
import com.coditas.learningmanagement.dto.response.LectureResponse;
import com.coditas.learningmanagement.service.LectureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lms/v1/lecture")
public class LectureController {

    private final LectureService lectureService;

    @PostMapping("/course/{courseId}")
    public ResponseEntity<ApplicationResponse<LectureResponse>> addNewLecture(@Valid@RequestBody LectureDto lectureDto, @PathVariable Long courseId){
        ApplicationResponse<LectureResponse>applicationResponse=new ApplicationResponse<>(lectureService.addNewLecture(lectureDto,courseId));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{lectureId}")
    public ResponseEntity<ApplicationResponse<LectureResponse>> deleteLecture(@PathVariable Long lectureId){
        ApplicationResponse<LectureResponse>applicationResponse=new ApplicationResponse<>(lectureService.deleteLecture(lectureId));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }

    @PatchMapping("/{lectureId}")
    public ResponseEntity<ApplicationResponse<LectureResponse>> updateLecture(@PathVariable Long lectureId, LectureUpdateRequest updates){
        ApplicationResponse<LectureResponse>applicationResponse=new ApplicationResponse<>(lectureService.updateLecture(lectureId,updates));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }


    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApplicationResponse<List<LectureDto>>> getAllLectures(@PathVariable Long courseId){
        ApplicationResponse<List<LectureDto>>applicationResponse=new ApplicationResponse<>(lectureService.getAllLectures(courseId));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }

}
