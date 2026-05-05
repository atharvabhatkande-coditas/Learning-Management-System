package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.dto.response.CourseResponseDto;
import com.coditas.learningmanagement.dto.response.ErrorResponse;
import com.coditas.learningmanagement.dto.response.GeneralResponse;
import com.coditas.learningmanagement.dto.response.ProgressResponse;
import com.coditas.learningmanagement.entity.Employee;
import com.coditas.learningmanagement.entity.LectureProgress;
import com.coditas.learningmanagement.entity.Lectures;
import com.coditas.learningmanagement.enums.LectureStatus;
import com.coditas.learningmanagement.exception.NotFoundException;
import com.coditas.learningmanagement.repository.CourseRepository;
import com.coditas.learningmanagement.repository.CustomUserDetailsRepository;
import com.coditas.learningmanagement.repository.LectureProgressRepository;
import com.coditas.learningmanagement.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.coditas.learningmanagement.constants.EnrollmentConstants.NOT_ENROLLED;
import static com.coditas.learningmanagement.constants.ExceptionConstants.USER_NOT_FOUND;
import static com.coditas.learningmanagement.constants.LectureConstants.*;

@Service
@RequiredArgsConstructor
public class LectureProgressService {
    private final LectureProgressRepository lectureProgressRepository;
    private final LectureRepository lectureRepository;
    private final CustomUserDetailsRepository customUserDetailsRepository;
    private final AuthService authService;

    public GeneralResponse startLecture(Long lectureId) {
        Lectures  lecture=lectureRepository.findById(lectureId).orElseThrow(()->new NotFoundException(LECTURE_NOT_FOUND));
        Employee employee=customUserDetailsRepository.findByUsername(authService.getUserDetails().getUsername()).orElseThrow(()->new NotFoundException(USER_NOT_FOUND));

        LectureProgress alreadyPresent=lectureProgressRepository.findByLectureAndEmployee(lecture,employee).orElse(null);
        if(alreadyPresent!=null){
            alreadyPresent.setLectureStatus(LectureStatus.IN_PROGRESS);
            lectureProgressRepository.save(alreadyPresent);
            return new GeneralResponse(LECTURE_STARTED);
        }

        LectureProgress lectureProgress=new LectureProgress();
        lectureProgress.setLectureStatus(LectureStatus.IN_PROGRESS);
        lectureProgress.setLecture(lecture);
        lectureProgress.setEmployee(employee);

        lecture.getLectureProgressList().add(lectureProgress);
        employee.getLectureProgressList().add(lectureProgress);
        lectureProgressRepository.save(lectureProgress);
        return new GeneralResponse(LECTURE_STARTED);
    }

    public GeneralResponse completeLecture(Long lectureId) {
        Lectures lecture=lectureRepository.findById(lectureId).orElseThrow(()->new NotFoundException(LECTURE_NOT_FOUND));
        Employee employee=customUserDetailsRepository.findByUsername(authService.getUserDetails().getUsername()).orElseThrow(()->new NotFoundException(USER_NOT_FOUND));

        LectureProgress lectureProgress=lectureProgressRepository.findByLectureAndEmployee(lecture,employee).orElse(null);
        if(lectureProgress==null){
            throw new NotFoundException(NOT_ENROLLED);
        }
        lectureProgress.setLectureStatus(LectureStatus.COMPLETED);
        lectureProgressRepository.save(lectureProgress);
        return new GeneralResponse(LECTURE_COMPLETED);
    }
}
