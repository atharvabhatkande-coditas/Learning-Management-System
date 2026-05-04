package com.coditas.learningmanagement.filter;

import com.coditas.learningmanagement.dto.response.ApplicationResponse;
import com.coditas.learningmanagement.dto.response.ErrorResponse;
import com.coditas.learningmanagement.exception.NotFoundException;
import com.coditas.learningmanagement.service.CustomUserDetailsService;
import com.coditas.learningmanagement.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final ObjectMapper objectMapper;


    @Override
    @NullMarked
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header=request.getHeader("Authorization");
        String username=null;
        String token=null;

        if(header!=null && header.startsWith("Bearer ")){
            token=header.substring(7);
            try{
                username=jwtUtil.extractUsername(token);

            }catch (JwtException e){
                response.setStatus(401);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ErrorResponse errorResponse=new ErrorResponse("Unauthorized",401);
                ApplicationResponse<List<ErrorResponse>> applicationResponse=new ApplicationResponse<>(List.of(errorResponse));
                response.getWriter().write(objectMapper.writeValueAsString(applicationResponse));
                return;
            }
        }

        if(jwtUtil.extractTypeFromToken(token).equals("refresh")){
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ErrorResponse errorResponse=new ErrorResponse("Unauthorized",401);
            ApplicationResponse<List<ErrorResponse>> applicationResponse=new ApplicationResponse<>(List.of(errorResponse));
            response.getWriter().write(objectMapper.writeValueAsString(applicationResponse));
            return;
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            try{
                UserDetails userDetails=customUserDetailsService.loadUserByUsername(username);
                if(jwtUtil.validateToken(userDetails,username,token)){
                    UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }catch (NotFoundException e){
                response.setStatus(401);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ErrorResponse errorResponse=new ErrorResponse("Unauthorized",401);
                ApplicationResponse<List<ErrorResponse>> applicationResponse=new ApplicationResponse<>(List.of(errorResponse));
                response.getWriter().write(objectMapper.writeValueAsString(applicationResponse));
                return;
            }

        }

        filterChain.doFilter(request,response);
    }

    }

