package com.coditas.learningmanagement.controller;

import com.coditas.learningmanagement.dto.request.CourseRequest;
import com.coditas.learningmanagement.dto.response.ApplicationResponse;
import com.coditas.learningmanagement.dto.response.CourseResponse;
import com.coditas.learningmanagement.dto.response.CourseResponseDto;
import com.coditas.learningmanagement.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lms/v1/course")
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ApplicationResponse<CourseResponse>>createNewCourse(@Valid @RequestBody CourseRequest courseRequest){
        ApplicationResponse<CourseResponse>applicationResponse=new ApplicationResponse<>(courseService.createNewCourse(courseRequest));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<ApplicationResponse<List<CourseResponseDto>>>getAllCourse(){
        ApplicationResponse<List<CourseResponseDto>> applicationResponse=new ApplicationResponse<>(courseService.getAllCourses());
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse<CourseResponseDto>>getAllCourse(@PathVariable Long id){
        ApplicationResponse<CourseResponseDto> applicationResponse=new ApplicationResponse<>(courseService.getCourse(id));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<ApplicationResponse<CourseResponse>>updateCoursePartial(@RequestBody Map<String,Object> updates, @PathVariable Long id){
        ApplicationResponse<CourseResponse> applicationResponse=new ApplicationResponse<>(courseService.updateCourse(updates,id));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApplicationResponse<CourseResponse>>deleteCourse(@PathVariable Long id){
        ApplicationResponse<CourseResponse> applicationResponse=new ApplicationResponse<>(courseService.deleteCourse(id));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }






}
