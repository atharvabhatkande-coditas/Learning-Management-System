package com.coditas.learningmanagement.repository;

import com.coditas.learningmanagement.entity.Assignment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    Optional<Assignment> findByTitleAndAssignmentLink(String title, @NotNull @NotBlank String assignmentLink);

    List<Assignment> findByCourse_CourseId(Long courseId);
}
