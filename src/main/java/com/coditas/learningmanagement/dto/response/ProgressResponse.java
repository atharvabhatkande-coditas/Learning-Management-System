package com.coditas.learningmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgressResponse {
    private CourseResponseDto enrolledCourse;
    private Double progress;
}
