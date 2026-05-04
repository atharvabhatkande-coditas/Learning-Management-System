package com.coditas.learningmanagement.dto.request;

import com.coditas.learningmanagement.dto.AssignmentRequestDto;
import com.coditas.learningmanagement.dto.LectureRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
    @NotNull(message = "Title cannot be null")
    @NotBlank(message = "Title cannot be empty")
    private String title;
    private String description;
    private String technology;
    private Integer duration;


    private List<LectureRequestDto> lectures;
    private List<AssignmentRequestDto>assignments;

}
