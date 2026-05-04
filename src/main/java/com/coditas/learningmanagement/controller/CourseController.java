package com.coditas.learningmanagement.controller;

import com.coditas.learningmanagement.dto.request.CourseRequest;
import com.coditas.learningmanagement.dto.response.ApplicationResponse;
import com.coditas.learningmanagement.dto.response.CourseResponse;
import com.coditas.learningmanagement.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lms/v1/course")
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ApplicationResponse<CourseResponse>>createNewCourse(@RequestBody CourseRequest courseRequest){
        ApplicationResponse<CourseResponse>applicationResponse=new ApplicationResponse<>(courseService.createNewCourse(courseRequest));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }


}
