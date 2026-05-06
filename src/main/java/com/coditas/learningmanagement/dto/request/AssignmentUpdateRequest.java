package com.coditas.learningmanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentUpdateRequest {

    private String assignmentLink;
    private String title;
    private LocalDate dueDate;
}
