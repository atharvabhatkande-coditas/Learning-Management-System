package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.dto.LectureDto;
import com.coditas.learningmanagement.dto.response.LectureResponse;
import com.coditas.learningmanagement.entity.Course;
import com.coditas.learningmanagement.exception.NotFoundException;
import com.coditas.learningmanagement.repository.CourseRepository;
import com.coditas.learningmanagement.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.coditas.learningmanagement.constants.DtoConstants.CREATED;
import static com.coditas.learningmanagement.constants.DtoConstants.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;


    public LectureResponse addNewLecture( LectureDto LectureDto,Long id) {
        Course course=courseRepository.findById(id).orElseThrow(()->new NotFoundException(NOT_FOUND));

        return new LectureResponse(CREATED);
    }
}
