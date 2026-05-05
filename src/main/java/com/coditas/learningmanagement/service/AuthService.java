package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.dto.request.LoginRequest;
import com.coditas.learningmanagement.dto.request.RegisterRequest;
import com.coditas.learningmanagement.dto.response.LoginResponseTokens;
import com.coditas.learningmanagement.dto.response.RegisterResponse;
import com.coditas.learningmanagement.entity.Employee;
import com.coditas.learningmanagement.entity.Otp;
import com.coditas.learningmanagement.entity.UniqueCode;
import com.coditas.learningmanagement.enums.RoleType;
import com.coditas.learningmanagement.exception.AlreadyExistException;
import com.coditas.learningmanagement.exception.AuthorizationException;
import com.coditas.learningmanagement.exception.NotFoundException;
import com.coditas.learningmanagement.mappers.EmployeeMapper;
import com.coditas.learningmanagement.repository.CustomUserDetailsRepository;
import com.coditas.learningmanagement.repository.OtpRepository;
import com.coditas.learningmanagement.repository.UniqueCodeRepository;
import com.coditas.learningmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.coditas.learningmanagement.constants.AuthConstants.*;
import static com.coditas.learningmanagement.constants.ExceptionConstants.USER_EXISTS;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final EmployeeMapper employeeMapper;
    private final CustomUserDetailsRepository customUserDetailsRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final OtpRepository otpRepository;
    private final UniqueCodeRepository uniqueCodeRepository;

    public RegisterResponse registerNewUser(RegisterRequest registerRequest){
        Otp otp=otpRepository.findByEmail(registerRequest.getUsername())
                .orElseThrow(()->new AuthorizationException(EMAIL_NOT_VERIFIED));

        if(!Objects.equals(registerRequest.getOtp(), otp.getOtpValue())){
            throw new AuthorizationException(OTP_INVALID);
        }
        if(otp.getExpireAt().before(new Date())){
            throw new AuthorizationException(OTP_EXPIRED);
        }

        Employee existEmployee=customUserDetailsRepository.findByUsername(registerRequest.getUsername()).orElse(null);
        if(existEmployee!=null){
            throw new AlreadyExistException(USER_EXISTS);
        }

        Set<RoleType> roles=new HashSet<>();
        if(registerRequest.getSecurityCode()==null){
            roles.add(RoleType.LEARNER);
        }
        else{
            UniqueCode uniqueCode=uniqueCodeRepository.findByEmail(registerRequest.getUsername())
                    .orElseThrow(()->new AuthorizationException(EMAIL_NOT_VERIFIED));

            if(!Objects.equals(uniqueCode.getCode(),registerRequest.getSecurityCode())){
                throw new AuthorizationException(CODE_EXPIRED);
            }
            if(uniqueCode.getExpireAt().before(new Date())){
                throw new AuthorizationException(CODE_EXPIRED);
            }

            roles.add(RoleType.ADMIN);
            roles.add(RoleType.LEARNER);
        }
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Employee employee=employeeMapper.toEntity(registerRequest);

        employee.setRoles(roles);
        employee.setEmployeeStatus(registerRequest.getEmployeeStatus());
        customUserDetailsRepository.save(employee);
        return new RegisterResponse(REGISTRATION_SUCCESS);

    }

    public LoginResponseTokens loginUser(LoginRequest loginRequest){
        try{
            Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
            UserDetails userDetails=(UserDetails) authentication.getPrincipal();
            if(Objects.isNull(userDetails)){
                throw new AuthorizationException(UNAUTHORIZED);
            }

           return jwtUtil.generateTokens(userDetails);
        }catch (Exception e){
            throw new AuthorizationException(UNAUTHORIZED);
        }
    }


    public UserDetails getUserDetails(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails=(UserDetails)authentication.getPrincipal();
        if(Objects.isNull(userDetails)){
            throw new AuthorizationException(UNAUTHORIZED);
        }
        return userDetails;
    }



}
