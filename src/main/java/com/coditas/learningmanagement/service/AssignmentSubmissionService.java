package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.dto.request.GeneralRequest;
import com.coditas.learningmanagement.dto.request.ScoresDto;
import com.coditas.learningmanagement.dto.response.AssignmentSubmissionDto;
import com.coditas.learningmanagement.dto.response.SingleResponse;
import com.coditas.learningmanagement.entity.*;
import com.coditas.learningmanagement.enums.EnrollmentStatus;
import com.coditas.learningmanagement.enums.SubmissionStatus;
import com.coditas.learningmanagement.exception.AlreadyExistException;
import com.coditas.learningmanagement.exception.ForbiddenException;
import com.coditas.learningmanagement.exception.NotFoundException;
import com.coditas.learningmanagement.mappers.AssignmentSubmissionMapper;
import com.coditas.learningmanagement.repository.AssignmentRepository;
import com.coditas.learningmanagement.repository.AssignmentSubmissionRepository;
import com.coditas.learningmanagement.repository.CustomUserDetailsRepository;
import com.coditas.learningmanagement.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.coditas.learningmanagement.constants.AssignmentConstants.*;
import static com.coditas.learningmanagement.constants.DtoConstants.UPDATED;
import static com.coditas.learningmanagement.constants.EnrollmentConstants.NOT_ENROLLED;
import static com.coditas.learningmanagement.constants.ExceptionConstants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AssignmentSubmissionService {
    private final AssignmentSubmissionRepository assignmentSubmissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final CustomUserDetailsRepository customUserDetailsRepository;
    private final AuthService authService;
    private final EnrollmentRepository enrollmentRepository;
    private final AssignmentSubmissionMapper assignmentSubmissionMapper;

    public SingleResponse submitAssignment(Long assignmentId, GeneralRequest request) {

        Assignment assignment=assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new NotFoundException(ASSIGNMENT_NOT_FOUND));

        Employee submittedBy=customUserDetailsRepository.findByUsername(authService.getUserDetails().getUsername())
                .orElseThrow(()->new NotFoundException(USER_NOT_FOUND));
        Course course=assignment.getCourse();

        Enrollment enrollment=enrollmentRepository.findByCourseAndEnrolledBy(course,submittedBy)
                .orElseThrow(()->new NotFoundException(NOT_ENROLLED));
        if(enrollment.getEnrollmentStatus()!= EnrollmentStatus.COMPLETED){
            throw new ForbiddenException(SUBMISSION_FORBIDDEN);
        }

        AssignmentSubmission assignmentSubmission= assignmentSubmissionRepository.findByAssignmentAndSubmittedBy(assignment,submittedBy)
                .orElse(null);

        if (!Objects.isNull(assignmentSubmission)){
            throw new AlreadyExistException(ALREADY_SUBMITTED);
        }

        assignmentSubmission=new AssignmentSubmission();
        assignmentSubmission.setAssignment(assignment);
        assignmentSubmission.setSubmittedBy(submittedBy);
        assignmentSubmission.setSubmissionStatus(SubmissionStatus.SUBMITTED);
        assignmentSubmission.setUrl(request.getValue());

        assignmentSubmissionRepository.save(assignmentSubmission);

        return new SingleResponse(ASSIGNMENT_SUBMITTED);

    }

    public List<AssignmentSubmissionDto> getSubmissions(Long assignmentId) {
        List<AssignmentSubmission>assignmentSubmissionList=assignmentSubmissionRepository.findByAssignment_AssignmentId(assignmentId);
        return assignmentSubmissionList.stream().map(assignmentSubmissionMapper::toDto).toList();
    }

    public SingleResponse updateScores(Long submissionId, ScoresDto scores) {
        AssignmentSubmission assignmentSubmission=assignmentSubmissionRepository.findById(submissionId)
                .orElseThrow(()->new NotFoundException(ASSIGNMENT_NOT_SUBMITTED));

        assignmentSubmission.setSubmissionStatus(SubmissionStatus.REVIEWED);
        assignmentSubmission.setScore(scores.getScores());
        assignmentSubmissionRepository.save(assignmentSubmission);
        return new SingleResponse(UPDATED);
    }
}
