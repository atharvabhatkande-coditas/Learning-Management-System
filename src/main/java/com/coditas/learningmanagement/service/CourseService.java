package com.coditas.learningmanagement.service;
import com.coditas.learningmanagement.dto.request.CourseRequest;
import com.coditas.learningmanagement.dto.request.UpdateCourseRequest;
import com.coditas.learningmanagement.dto.response.CourseResponse;
import com.coditas.learningmanagement.dto.response.CourseResponseDto;
import com.coditas.learningmanagement.entity.Course;
import com.coditas.learningmanagement.entity.Employee;
import com.coditas.learningmanagement.exception.AuthorizationException;
import com.coditas.learningmanagement.exception.NotFoundException;
import com.coditas.learningmanagement.mappers.CourseMapper;
import com.coditas.learningmanagement.repository.CourseRepository;
import com.coditas.learningmanagement.repository.CustomUserDetailsRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.coditas.learningmanagement.constants.AuthConstants.UNAUTHORIZED;
import static com.coditas.learningmanagement.constants.DtoConstants.*;


@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final CustomUserDetailsRepository customUserDetailsRepository;
    private final AuthService authService;
    private final ObjectMapper objectMapper;


    public CourseResponse createNewCourse(CourseRequest courseRequest) {
        Employee createdBy=customUserDetailsRepository.findByUsername(authService.getUserDetails().getUsername())
                .orElseThrow(()->new AuthorizationException(UNAUTHORIZED));
        Course course=courseMapper.toEntity(courseRequest);
        course.getLectures().forEach(lectures -> lectures.setCourse(course));
        course.setCreatedBy(createdBy);
        courseRepository.save(course);
        return new CourseResponse("Course"+ CREATED);
    }

    public List<CourseResponseDto> getAllCourses() {
        return courseRepository.findAll().stream().map(courseMapper::toDto).toList();
    }


    public CourseResponseDto getCourse(Long id) {
        Course course= courseRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(NOT_FOUND));
        return courseMapper.toDto(course);
    }


    public CourseResponse updateCourse(UpdateCourseRequest updates, Long id) {
        Course course=courseRepository.findById(id)
                .orElseThrow(()->new NotFoundException(NOT_FOUND));

        if(!Objects.isNull(updates.getDescription())){
            course.setDescription(updates.getDescription());
        }
        if(!Objects.isNull(updates.getDuration())){
            course.setDuration(updates.getDuration());
        }
        if(!Objects.isNull(updates.getTechnology())){
            course.setTechnology(updates.getTechnology());
        }
        if(!Objects.isNull(updates.getTitle())){
            course.setTitle(updates.getTitle());
        }
        courseRepository.save(course);


        return new CourseResponse(UPDATED);
    }

    public CourseResponse deleteCourse(Long id) {
        Course course=courseRepository.findById(id)
                .orElseThrow(()->new NotFoundException(NOT_FOUND));
        courseRepository.delete(course);
        return new CourseResponse(DELETED);
    }
}
