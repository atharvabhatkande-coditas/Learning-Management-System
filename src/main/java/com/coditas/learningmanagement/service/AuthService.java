package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.dto.request.GeneralRequest;
import com.coditas.learningmanagement.dto.request.LoginRequest;
import com.coditas.learningmanagement.dto.request.RegisterRequest;
import com.coditas.learningmanagement.dto.response.GeneralResponse;
import com.coditas.learningmanagement.dto.response.LoginResponseTokens;
import com.coditas.learningmanagement.dto.response.RegisterResponse;
import com.coditas.learningmanagement.entity.Employee;
import com.coditas.learningmanagement.entity.Otp;
import com.coditas.learningmanagement.entity.RefreshToken;
import com.coditas.learningmanagement.entity.UniqueCode;
import com.coditas.learningmanagement.enums.RoleType;
import com.coditas.learningmanagement.exception.AlreadyExistException;
import com.coditas.learningmanagement.exception.AuthorizationException;
import com.coditas.learningmanagement.mappers.EmployeeMapper;
import com.coditas.learningmanagement.repository.CustomUserDetailsRepository;
import com.coditas.learningmanagement.repository.OtpRepository;
import com.coditas.learningmanagement.repository.RefreshTokenRepository;
import com.coditas.learningmanagement.repository.UniqueCodeRepository;
import com.coditas.learningmanagement.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.*;

import static com.coditas.learningmanagement.constants.AuthConstants.*;
import static com.coditas.learningmanagement.constants.ExceptionConstants.*;

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
    private final RefreshTokenRepository refreshTokenRepository;

    public RegisterResponse registerNewUser(RegisterRequest registerRequest){

        if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())){
            throw new AuthorizationException(CHECK_PASSWORD);
        }

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
                throw new AuthorizationException(CODE_INVALID);
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
                throw new AuthorizationException(LOGIN_FAILURE);
            }

           return jwtUtil.generateTokens(userDetails);
        }catch (Exception e){
            throw new AuthorizationException(LOGIN_FAILURE);
        }
    }


    public UserDetails getUserDetails(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(authentication)){
            throw new AuthorizationException(UNAUTHORIZED);
        }
        UserDetails userDetails=(UserDetails)authentication.getPrincipal();
        if(Objects.isNull(userDetails)){
            throw new AuthorizationException(UNAUTHORIZED);
        }
        return userDetails;
    }


    public GeneralResponse generateAccessToken(@Valid GeneralRequest generalRequest) {
        RefreshToken refreshToken=refreshTokenRepository.findByUsername(getUserDetails().getUsername()).orElse(null);
        if(refreshToken!=null && !generalRequest.getValue().equals(refreshToken.getToken())){
                throw new AuthorizationException(RE_LOGIN);
        }

        refreshToken=new RefreshToken();
        List<String>roles=getUserDetails().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String accessToken=jwtUtil.generateTokenInternal(getUserDetails().getUsername(),roles,1000L*60*60,"access");
        refreshToken.setToken(accessToken);
        refreshToken.setUsername(getUserDetails().getUsername());
        refreshTokenRepository.save(refreshToken);
        return new GeneralResponse(accessToken);
    }

    public GeneralResponse logout() {
        RefreshToken refreshToken=refreshTokenRepository.findByUsername(getUserDetails().getUsername()).orElse(null);
        if(refreshToken==null){
            return new GeneralResponse(LOGOUT);
        }
        refreshToken.setToken(null);
        refreshTokenRepository.save(refreshToken);
        return new GeneralResponse(LOGOUT);
    }
}
