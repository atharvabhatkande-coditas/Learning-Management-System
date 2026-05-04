package com.coditas.learningmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.coditas.learningmanagement.constants.DtoConstants.NOT_EMPTY;
import static com.coditas.learningmanagement.constants.DtoConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull(message = NOT_EMPTY)
    @NotBlank(message = NOT_NULL)
    private String username;
    @NotNull(message = NOT_EMPTY)
    @NotBlank(message = NOT_NULL)
    private String password;
}
