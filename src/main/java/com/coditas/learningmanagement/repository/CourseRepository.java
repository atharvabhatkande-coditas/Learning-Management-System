package com.coditas.learningmanagement.repository;

import com.coditas.learningmanagement.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
