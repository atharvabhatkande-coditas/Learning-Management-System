package com.coditas.learningmanagement.dto;

import com.coditas.learningmanagement.enums.ContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import static com.coditas.learningmanagement.constants.DtoConstants.NOT_EMPTY;
import static com.coditas.learningmanagement.constants.DtoConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LectureDto {
    @NotNull(message = NOT_EMPTY)
    @NotBlank(message = NOT_NULL)
    private String title;


    private ContentType contentType;

    @NotNull(message = NOT_EMPTY)
    @NotBlank(message = NOT_NULL)
    private String lectureLink;
}
