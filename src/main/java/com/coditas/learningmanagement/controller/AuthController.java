package com.coditas.learningmanagement.controller;

import com.coditas.learningmanagement.dto.request.GeneralRequest;
import com.coditas.learningmanagement.dto.request.LoginRequest;
import com.coditas.learningmanagement.dto.request.RegisterRequest;
import com.coditas.learningmanagement.dto.response.ApplicationResponse;
import com.coditas.learningmanagement.dto.response.GeneralResponse;
import com.coditas.learningmanagement.dto.response.LoginResponseTokens;
import com.coditas.learningmanagement.dto.response.RegisterResponse;
import com.coditas.learningmanagement.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lms/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApplicationResponse<RegisterResponse>> registerNewUser(@Valid @RequestBody RegisterRequest registerRequest){
        ApplicationResponse<RegisterResponse> applicationResponse=new ApplicationResponse<>(authService.registerNewUser(registerRequest));
        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApplicationResponse<LoginResponseTokens>> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        ApplicationResponse<LoginResponseTokens> applicationResponse=new ApplicationResponse<>(authService.loginUser(loginRequest));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }


    @PostMapping("/refresh")
    public ResponseEntity<ApplicationResponse<GeneralResponse>> generateAccessToken(@Valid @RequestBody GeneralRequest generalRequest){
        ApplicationResponse<GeneralResponse> applicationResponse=new ApplicationResponse<>(authService.generateAccessToken(generalRequest));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApplicationResponse<GeneralResponse>> generateAccessToken(){
        ApplicationResponse<GeneralResponse> applicationResponse=new ApplicationResponse<>(authService.logout());
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }


}
