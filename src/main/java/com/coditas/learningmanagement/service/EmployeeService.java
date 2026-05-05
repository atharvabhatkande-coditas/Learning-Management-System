package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.constants.DtoConstants;
import com.coditas.learningmanagement.dto.response.EmployeeDto;
import com.coditas.learningmanagement.dto.response.ErrorResponse;
import com.coditas.learningmanagement.dto.response.GeneralResponse;
import com.coditas.learningmanagement.entity.Employee;
import com.coditas.learningmanagement.exception.AuthorizationException;
import com.coditas.learningmanagement.mappers.EmployeeMapper;
import com.coditas.learningmanagement.repository.CustomUserDetailsRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.coditas.learningmanagement.constants.ExceptionConstants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final CustomUserDetailsRepository customUserDetailsRepository;
    private final AuthService authService;
    private final EmployeeMapper employeeMapper;
    private final ObjectMapper objectMapper;

    public EmployeeDto getProfile() {
        Employee employee=customUserDetailsRepository.findByUsername(authService.getUserDetails().getUsername())
                .orElseThrow(()->new AuthorizationException(USER_NOT_FOUND));

        return employeeMapper.toDto(employee);
    }

    public GeneralResponse updateProfile(Map<String, Object>updates)   {
        Employee employee=customUserDetailsRepository.findByUsername(authService.getUserDetails().getUsername())
                .orElseThrow(()->new AuthorizationException(USER_NOT_FOUND));

        try {
            objectMapper.updateValue(employee,updates);
            customUserDetailsRepository.save(employee);

            return new GeneralResponse(DtoConstants.UPDATED);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        }
    }
}