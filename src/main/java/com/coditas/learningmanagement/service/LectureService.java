package com.coditas.learningmanagement.service;


import com.coditas.learningmanagement.dto.LectureDto;
import com.coditas.learningmanagement.dto.request.LectureUpdateRequest;
import com.coditas.learningmanagement.dto.response.LectureResponse;
import com.coditas.learningmanagement.entity.Course;
import com.coditas.learningmanagement.entity.Lectures;
import com.coditas.learningmanagement.exception.AlreadyExistException;
import com.coditas.learningmanagement.exception.NotFoundException;
import com.coditas.learningmanagement.mappers.LectureMapper;
import com.coditas.learningmanagement.repository.CourseRepository;
import com.coditas.learningmanagement.repository.LectureRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.coditas.learningmanagement.constants.DtoConstants.*;

@Service
@RequiredArgsConstructor
@RequestMapping("/lms/v1/lecture")
public class LectureService {
    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;
    private final LectureMapper lectureMapper;

    private final ObjectMapper objectMapper;

    public LectureResponse addNewLecture( LectureDto lectureDto,Long courseId) {
        Course course=courseRepository.findById(courseId)
                .orElseThrow(()->new NotFoundException("Course"+NOT_FOUND));
        Lectures existingLecture=lectureRepository.findByTitleAndLectureLink(lectureDto.getTitle(),lectureDto.getLectureLink())
                .orElse(null);
        if(!Objects.isNull(existingLecture)){
            throw new AlreadyExistException(EXIST);
        }
        Lectures lecture=lectureMapper.toEntity(lectureDto);
        lecture.setCourse(course);
        course.getLectures().add(lecture);
        lectureRepository.save(lecture);
        return new LectureResponse(CREATED);
    }

    public LectureResponse deleteLecture(Long lectureId) {
        Lectures lecture=lectureRepository.findById(lectureId)
                .orElseThrow(()->new NotFoundException(NOT_FOUND));
        lectureRepository.delete(lecture);
        return new LectureResponse(DELETED);
    }

    public LectureResponse updateLecture(Long lectureId, LectureUpdateRequest updates) {
        Lectures lecture=lectureRepository.findById(lectureId)
                .orElseThrow(()->new NotFoundException(NOT_FOUND));
       if(!Objects.isNull(updates.getLectureLink())){
           lecture.setLectureLink(updates.getLectureLink());
       }
        if(!Objects.isNull(updates.getTitle())){
            lecture.setTitle(updates.getTitle());
        }
        lectureRepository.save(lecture);

        return  new LectureResponse(UPDATED);

    }

    public List<LectureDto> getAllLectures(Long courseId) {
        Course course=courseRepository.findById(courseId)
                .orElseThrow(()->new NotFoundException(NOT_FOUND));
        List<Lectures>lectures=lectureRepository.findByCourse(course);
        return lectures.stream().map(lectureMapper::toDto).toList();
    }
}
