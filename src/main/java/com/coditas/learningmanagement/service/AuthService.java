package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.dto.request.LoginRequest;
import com.coditas.learningmanagement.dto.request.RegisterRequest;
import com.coditas.learningmanagement.dto.response.LoginResponseTokens;
import com.coditas.learningmanagement.dto.response.RegisterResponse;
import com.coditas.learningmanagement.entity.Employee;
import com.coditas.learningmanagement.enums.RoleType;
import com.coditas.learningmanagement.exception.AlreadyExistException;
import com.coditas.learningmanagement.exception.AuthorizationException;
import com.coditas.learningmanagement.mappers.EmployeeMapper;
import com.coditas.learningmanagement.repository.CustomUserDetailsRepository;
import com.coditas.learningmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.coditas.learningmanagement.constants.AuthConstants.REGISTRATION_SUCCESS;
import static com.coditas.learningmanagement.constants.AuthConstants.UNAUTHORIZED;
import static com.coditas.learningmanagement.constants.ExceptionConstants.USER_EXISTS;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final EmployeeMapper employeeMapper;
    private final CustomUserDetailsRepository customUserDetailsRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse registerNewUser(RegisterRequest registerRequest){
        Employee existEmployee=customUserDetailsRepository.findByUsername(registerRequest.getUsername()).orElse(null);
        if(existEmployee!=null){
            throw new AlreadyExistException(USER_EXISTS);
        }

        Set<RoleType> roles=new HashSet<>();
        if(registerRequest.getSecurityCode()==null){
            roles.add(RoleType.LEARNER);
        }
        else{
            roles.add(RoleType.ADMIN);
            roles.add(RoleType.LEARNER);
        }
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Employee employee=employeeMapper.toEntity(registerRequest);

        employee.setRoles(roles);
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
        }catch (BadCredentialsException e){
            throw new AuthorizationException(UNAUTHORIZED);
        }
    }



}
