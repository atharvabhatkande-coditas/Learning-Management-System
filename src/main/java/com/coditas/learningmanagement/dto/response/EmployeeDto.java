package com.coditas.learningmanagement.dto.response;

import com.coditas.learningmanagement.enums.DepartmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private String username;
    private String firstName;
    private String lastName;
    private DepartmentType department;
}
