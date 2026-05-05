package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.dto.response.ErrorResponse;
import com.coditas.learningmanagement.dto.response.GeneralResponse;
import com.coditas.learningmanagement.dto.response.ProgressResponse;
import com.coditas.learningmanagement.entity.Course;
import com.coditas.learningmanagement.entity.Employee;
import com.coditas.learningmanagement.entity.Enrollment;
import com.coditas.learningmanagement.enums.EnrollmentStatus;
import com.coditas.learningmanagement.exception.AlreadyExistException;
import com.coditas.learningmanagement.exception.NotFoundException;
import com.coditas.learningmanagement.mappers.CourseMapper;
import com.coditas.learningmanagement.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.coditas.learningmanagement.constants.DtoConstants.NOT_FOUND;
import static com.coditas.learningmanagement.constants.EnrollmentConstants.*;
import static com.coditas.learningmanagement.constants.ExceptionConstants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CustomUserDetailsRepository customUserDetailsRepository;
    private final CourseRepository courseRepository;
    private final AuthService authService;
    private final LectureRepository lectureRepository;
    private final LectureProgressRepository lectureProgressRepository;
    private final CourseMapper courseMapper;


    public GeneralResponse enrollIntoCourse(Long courseId) {
        Course course=courseRepository.findById(courseId).orElseThrow(()->new NotFoundException(NOT_FOUND));
        Employee enrolledBy=customUserDetailsRepository.findByUsername(authService.getUserDetails().getUsername()).orElseThrow(()->new NotFoundException(USER_NOT_FOUND));
        Enrollment alreadyEnrolled=enrollmentRepository.findByCourseAndEnrolledBy(course,enrolledBy).orElse(null);
        if(alreadyEnrolled!=null){
            throw new AlreadyExistException(ALREADY_ENROLLED);
        }
        Enrollment enrollment=new Enrollment();
        enrollment.setEnrolledBy(enrolledBy);
        enrollment.setCourse(course);
        enrollment.setEnrollmentStatus(EnrollmentStatus.ENROLLED);
        enrollment.setProgress(0.0);
        enrolledBy.getEnrollments().add(enrollment);
        course.getEnrollmentList().add(enrollment);

        enrollmentRepository.save(enrollment);
        return  new GeneralResponse(ENROLLED);

    }

    public ProgressResponse getProgress(Long enrollmentId) {
        Enrollment enrollment=enrollmentRepository.findById(enrollmentId).orElseThrow(()->new NotFoundException(NOT_ENROLLED));
        Long courseId=enrollment.getCourse().getCourseId();
        Long totalLectures=lectureRepository.countByCourse_CourseId(courseId);
        Long lecturesCompleted=lectureProgressRepository.countCompleted(enrollment.getEnrolledBy().getEmployeeId(),courseId);

        double progress= (double)lecturesCompleted*100/totalLectures;
        enrollment.setProgress(progress);
        if(progress==100.0){
        enrollment.setEnrollmentStatus(EnrollmentStatus.COMPLETED);
        enrollment.setEndDate(LocalDate.now());
        enrollmentRepository.save(enrollment);
        return new ProgressResponse(courseMapper.toDto(enrollment.getCourse()),progress);
        }

        enrollment.setEnrollmentStatus(EnrollmentStatus.IN_PROGRESS);
        enrollmentRepository.save(enrollment);
        return new ProgressResponse(courseMapper.toDto(enrollment.getCourse()),progress);

    }
}
