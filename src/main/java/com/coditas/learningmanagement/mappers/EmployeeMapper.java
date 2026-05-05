package com.coditas.learningmanagement.mappers;

import com.coditas.learningmanagement.dto.request.RegisterRequest;
import com.coditas.learningmanagement.dto.response.EmployeeDto;
import com.coditas.learningmanagement.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEntity(RegisterRequest registerRequest);

    EmployeeDto toDto(Employee employee);
}
