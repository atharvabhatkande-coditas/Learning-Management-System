package com.coditas.learningmanagement.security;

import com.coditas.learningmanagement.enums.RoleType;
import com.coditas.learningmanagement.filter.JwtFilter;
import com.coditas.learningmanagement.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.coditas.learningmanagement.constants.EndPoints.*;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(AUTH).permitAll()
                        .requestMatchers("/lms/v1/email/sendOtp").permitAll()

                        //superadmin
                        .requestMatchers("/lms/v1/email/sendCode").hasRole(RoleType.SUPERADMIN.name())

                        //learner
                        .requestMatchers(HttpMethod.GET,COURSE).hasRole(RoleType.LEARNER.name())
                        .requestMatchers(HttpMethod.GET,LECTURE).hasRole(RoleType.LEARNER.name())
                        .requestMatchers(LECTURE_PROGRESS).hasRole(RoleType.LEARNER.name())
                        .requestMatchers(ENROLL).hasRole(RoleType.LEARNER.name())
                        .requestMatchers(EMPLOYEE).hasRole(RoleType.LEARNER.name())
                        .requestMatchers(HttpMethod.GET,ASSIGNMENT).hasRole(RoleType.LEARNER.name())


                        //admin
                        .requestMatchers(HttpMethod.POST,COURSE).hasRole(RoleType.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,COURSE).hasRole(RoleType.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH,COURSE).hasRole(RoleType.ADMIN.name())

                        .requestMatchers(HttpMethod.POST,LECTURE).hasRole(RoleType.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,LECTURE).hasRole(RoleType.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH,LECTURE).hasRole(RoleType.ADMIN.name())

                        .requestMatchers(HttpMethod.POST,ASSIGNMENT).hasRole(RoleType.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH,ASSIGNMENT).hasRole(RoleType.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,ASSIGNMENT).hasRole(RoleType.ADMIN.name())









                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex->
                    ex.authenticationEntryPoint((request, response, authException) ->
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                            )
                        )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }
}
