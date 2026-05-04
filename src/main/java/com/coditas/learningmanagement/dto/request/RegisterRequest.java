package com.coditas.learningmanagement.dto.request;

import com.coditas.learningmanagement.enums.DepartmentType;
import com.coditas.learningmanagement.enums.EmployeeStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.coditas.learningmanagement.constants.DtoConstants.NOT_EMPTY;
import static com.coditas.learningmanagement.constants.DtoConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull(message = NOT_EMPTY)
    @NotBlank(message = NOT_NULL)
    @Email(message = "Invalid Email format")
    private String username;

    @NotNull(message = NOT_EMPTY)
    @NotBlank(message = NOT_NULL)
    @Size(min = 8,message = "Password min 8 characters required")
    private String password;

    private String firstName;
    private String lastName;
    private DepartmentType department;

    private String securityCode;

    private EmployeeStatus employeeStatus;
}
