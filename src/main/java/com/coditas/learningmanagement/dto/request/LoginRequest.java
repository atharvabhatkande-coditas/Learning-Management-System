package com.coditas.learningmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull(message = "Username cannot be empty")
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Password cannot be empty")
    @NotNull(message = "Password cannot be null")
    private String password;
}
