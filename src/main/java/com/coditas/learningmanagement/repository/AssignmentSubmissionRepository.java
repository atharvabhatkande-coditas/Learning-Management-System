package com.coditas.learningmanagement.repository;

import com.coditas.learningmanagement.entity.Assignment;
import com.coditas.learningmanagement.entity.AssignmentSubmission;
import com.coditas.learningmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission,Long> {
    Optional<AssignmentSubmission> findByAssignmentAndSubmittedBy(Assignment assignment, Employee submittedBy);

    List<AssignmentSubmission> findByAssignment_AssignmentId(Long assignmentId);
}
