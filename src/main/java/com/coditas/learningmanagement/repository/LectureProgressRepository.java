package com.coditas.learningmanagement.repository;

import com.coditas.learningmanagement.entity.Employee;
import com.coditas.learningmanagement.entity.LectureProgress;
import com.coditas.learningmanagement.entity.Lectures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LectureProgressRepository extends JpaRepository<LectureProgress,Long> {
    Optional<LectureProgress> findByLectureAndEmployee(Lectures lecture, Employee employee);
    @Query("""
            SELECT COUNT(lp)
            FROM LectureProgress lp
            WHERE lp.employee.id = :employeeId
            AND lp.lecture.course.id = :courseId
            AND lp.lectureStatus = com.coditas.learningmanagement.enums.LectureStatus.COMPLETED
            """)
    Long countCompleted(Long employeeId, Long courseId);
}
