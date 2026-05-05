package com.coditas.learningmanagement.mappers;

import com.coditas.learningmanagement.dto.LectureDto;
import com.coditas.learningmanagement.entity.Lectures;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LectureMapper {
    Lectures toEntity(LectureDto lectureDto);
    LectureDto toDto(Lectures lecture);
}
