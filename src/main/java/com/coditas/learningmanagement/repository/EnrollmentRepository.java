package com.coditas.learningmanagement.repository;

import com.coditas.learningmanagement.entity.Course;
import com.coditas.learningmanagement.entity.Employee;
import com.coditas.learningmanagement.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    Optional<Enrollment> findByCourseAndEnrolledBy(Course course, Employee enrolledBy);
}
