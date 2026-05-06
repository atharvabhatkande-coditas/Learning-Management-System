package com.coditas.learningmanagement.mappers;

import com.coditas.learningmanagement.dto.response.AssignmentSubmissionDto;
import com.coditas.learningmanagement.entity.AssignmentSubmission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssignmentSubmissionMapper {
    AssignmentSubmissionDto toDto(AssignmentSubmission assignmentSubmission);
}
