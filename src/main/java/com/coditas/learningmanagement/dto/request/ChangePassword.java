package com.coditas.learningmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ChangePassword {

    @NotNull(message = NOT_EMPTY)
    @NotBlank(message = NOT_NULL)
    private String previousPassword;

    @NotNull(message = NOT_EMPTY)
    @NotBlank(message = NOT_NULL)
    @Size(min = 8,message = "Password min 8 characters required")
    private String newPassword;

    @NotNull(message = NOT_EMPTY)
    @NotBlank(message = NOT_NULL)
    @Size(min = 8,message = "Password min 8 characters required")
    private String confirmPassword;
}
