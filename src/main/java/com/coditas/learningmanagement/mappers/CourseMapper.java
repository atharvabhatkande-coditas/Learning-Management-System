package com.coditas.learningmanagement.mappers;

import com.coditas.learningmanagement.dto.request.CourseRequest;
import com.coditas.learningmanagement.dto.response.CourseResponseDto;
import com.coditas.learningmanagement.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    Course toEntity(CourseRequest courseRequest);
    CourseResponseDto toDto(Course course);
}
