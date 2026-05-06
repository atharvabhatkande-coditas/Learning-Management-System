package com.coditas.learningmanagement.mappers;

import com.coditas.learningmanagement.dto.response.CertificateDto;
import com.coditas.learningmanagement.entity.Certificate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CertificateMapper {
    CertificateDto toDto(Certificate certificate);
}
