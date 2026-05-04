package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.constants.ExceptionConstants;
import com.coditas.learningmanagement.exception.NotFoundException;
import com.coditas.learningmanagement.repository.CustomUserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final CustomUserDetailsRepository customUserDetailsRepository;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username){
       return customUserDetailsRepository.findByUsername(username).orElseThrow(()->new NotFoundException(ExceptionConstants.USER_NOT_FOUND));
    }
}
