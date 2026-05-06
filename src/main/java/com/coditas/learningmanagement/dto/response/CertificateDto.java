package com.coditas.learningmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDto {

    private Long certificateId;
    private LocalDate issuedAt;
    private CourseResponseDto course;
}
