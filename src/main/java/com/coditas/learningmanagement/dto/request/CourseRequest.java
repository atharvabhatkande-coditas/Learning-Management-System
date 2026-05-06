package com.coditas.learningmanagement.dto.request;

import com.coditas.learningmanagement.dto.LectureDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @NotNull
    @Size(min =1,message = "Required least one lecture")
    private List<LectureDto> lectures;
}
