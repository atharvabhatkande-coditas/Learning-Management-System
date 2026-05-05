package com.coditas.learningmanagement.repository;

import com.coditas.learningmanagement.entity.Course;
import com.coditas.learningmanagement.entity.Lectures;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lectures,Long> {
    Optional<Lectures> findByTitleAndLectureLink(String title, String lectureLink);

    List<Lectures> findByCourse(Course course);

    Long countByCourse_CourseId(Long courseId);
}
