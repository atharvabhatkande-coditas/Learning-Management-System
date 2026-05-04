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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Username cannot be empty")
    @NotNull(message = "Username cannot be null")
    @Email(message = "Invalid Email format")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @NotNull(message = "Password cannot be null")
    @Size(min = 8,message = "Password min 8 characters required")
    private String password;

    private String firstName;
    private String lastName;
    private DepartmentType department;

    private String securityCode;

    private EmployeeStatus employeeStatus;
}
