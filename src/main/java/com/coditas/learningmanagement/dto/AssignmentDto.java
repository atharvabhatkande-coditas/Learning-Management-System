package com.coditas.learningmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDto {
    private String title;
    private LocalDate dueDate;
    @NotNull
    @NotBlank
    private String assignmentLink;


}
