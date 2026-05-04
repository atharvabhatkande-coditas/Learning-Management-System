package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.dto.request.CourseRequest;
import com.coditas.learningmanagement.dto.response.CourseResponse;
import com.coditas.learningmanagement.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseResponse createNewCourse(CourseRequest courseRequest) {


        return new CourseResponse();
    }
}
