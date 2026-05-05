package com.coditas.learningmanagement.controller;

import com.coditas.learningmanagement.dto.request.ChangePassword;
import com.coditas.learningmanagement.dto.response.ApplicationResponse;
import com.coditas.learningmanagement.dto.response.EmployeeDto;
import com.coditas.learningmanagement.dto.response.GeneralResponse;
import com.coditas.learningmanagement.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lms/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/profile")
    public ResponseEntity<ApplicationResponse<EmployeeDto>>getProfile(){
        ApplicationResponse<EmployeeDto>applicationResponse=new ApplicationResponse<>(employeeService.getProfile());
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }

    @PatchMapping("/profile")
    public ResponseEntity<ApplicationResponse<GeneralResponse>>updateProfile(@RequestBody Map<String,Object> updates)   {
        ApplicationResponse<GeneralResponse>applicationResponse=new ApplicationResponse<>(employeeService.updateProfile(updates));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }

    @PatchMapping("/profile/password")
    public ResponseEntity<ApplicationResponse<GeneralResponse>>changePassword(@Valid @RequestBody ChangePassword changePassword)   {
        ApplicationResponse<GeneralResponse>applicationResponse=new ApplicationResponse<>(employeeService.changePassword(changePassword));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }


}
