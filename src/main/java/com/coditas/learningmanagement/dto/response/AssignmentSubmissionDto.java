package com.coditas.learningmanagement.dto.response;


import com.coditas.learningmanagement.enums.SubmissionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentSubmissionDto {
    private Long submissionId;
    private Integer score;
    private String url;

    private SubmissionStatus submissionStatus;

    private EmployeeDto submittedBy;
}
