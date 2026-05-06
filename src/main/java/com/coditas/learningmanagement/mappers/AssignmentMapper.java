package com.coditas.learningmanagement.mappers;

import com.coditas.learningmanagement.dto.AssignmentDto;
import com.coditas.learningmanagement.dto.request.AssignmentUpdateRequest;
import com.coditas.learningmanagement.entity.Assignment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    AssignmentDto toDto(Assignment assignment);
}
