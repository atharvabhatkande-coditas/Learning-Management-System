package com.coditas.learningmanagement.repository;

import com.coditas.learningmanagement.entity.Employee;
import com.coditas.learningmanagement.entity.LectureProgress;
import com.coditas.learningmanagement.entity.Lectures;
import com.coditas.learningmanagement.enums.LectureStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LectureProgressRepository extends JpaRepository<LectureProgress,Long> {
    Optional<LectureProgress> findByLectureAndEmployee(Lectures lecture, Employee employee);

    @Query("SELECT COUNT(lp.id) FROM LectureProgress lp WHERE lp.employee.id = :employeeId AND lp.lecture.course.id = :courseId AND lp.lectureStatus = :status")
    long countCompletedLectures(Long employeeId, Long courseId, LectureStatus status);

}
