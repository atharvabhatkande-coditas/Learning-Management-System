package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.constants.DtoConstants;
import com.coditas.learningmanagement.dto.request.ChangePassword;
import com.coditas.learningmanagement.dto.request.ProfileUpdateRequest;
import com.coditas.learningmanagement.dto.response.EmployeeDto;
import com.coditas.learningmanagement.dto.response.SingleResponse;
import com.coditas.learningmanagement.entity.Employee;
import com.coditas.learningmanagement.exception.AuthorizationException;
import com.coditas.learningmanagement.mappers.EmployeeMapper;
import com.coditas.learningmanagement.repository.CustomUserDetailsRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.Objects;

import static com.coditas.learningmanagement.constants.AuthConstants.CHECK_PASSWORD;
import static com.coditas.learningmanagement.constants.ExceptionConstants.PASSWORD_IDENTICAL;
import static com.coditas.learningmanagement.constants.ExceptionConstants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final CustomUserDetailsRepository customUserDetailsRepository;
    private final AuthService authService;
    private final EmployeeMapper employeeMapper;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public EmployeeDto getProfile() {
        Employee employee=customUserDetailsRepository.findByUsername(authService.getUserDetails().getUsername())
                .orElseThrow(()->new AuthorizationException(USER_NOT_FOUND));

        return employeeMapper.toDto(employee);
    }

    public SingleResponse updateProfile(ProfileUpdateRequest updates)   {
        Employee employee=customUserDetailsRepository.findByUsername(authService.getUserDetails().getUsername())
                .orElseThrow(()->new AuthorizationException(USER_NOT_FOUND));

       if(!Objects.isNull(updates.getFirstName())){
           employee.setFirstName(updates.getFirstName());
       }
        if(!Objects.isNull(updates.getLastName())){
            employee.setLastName(updates.getLastName());
        }
        customUserDetailsRepository.save(employee);
        return new SingleResponse(DtoConstants.UPDATED);

    }

    public SingleResponse changePassword(ChangePassword changePassword) {
        if(!changePassword.getNewPassword().equals(changePassword.getConfirmPassword())){
            throw new AuthorizationException(CHECK_PASSWORD);
        }

        Employee employee=customUserDetailsRepository.findByUsername(authService.getUserDetails().getUsername())
                .orElseThrow(()->new AuthorizationException(USER_NOT_FOUND));

        if(passwordEncoder.matches(changePassword.getNewPassword(),employee.getPassword())){
            throw new AuthorizationException(PASSWORD_IDENTICAL);
        }

        employee.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        customUserDetailsRepository.save(employee);
        return new SingleResponse(DtoConstants.UPDATED);

    }
}