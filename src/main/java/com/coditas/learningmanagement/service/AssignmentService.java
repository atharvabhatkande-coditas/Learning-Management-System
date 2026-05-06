package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.dto.AssignmentDto;
import com.coditas.learningmanagement.dto.request.AssignmentUpdateRequest;
import com.coditas.learningmanagement.dto.response.SingleResponse;
import com.coditas.learningmanagement.entity.Assignment;
import com.coditas.learningmanagement.entity.Course;

import com.coditas.learningmanagement.exception.AlreadyExistException;
import com.coditas.learningmanagement.exception.NotFoundException;
import com.coditas.learningmanagement.mappers.AssignmentMapper;
import com.coditas.learningmanagement.repository.AssignmentRepository;
import com.coditas.learningmanagement.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.coditas.learningmanagement.constants.DtoConstants.*;



@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final AssignmentMapper assignmentMapper;

    public SingleResponse createAssignment(AssignmentDto assignmentDto, Long courseId) {
        Course course=courseRepository.findById(courseId)
                .orElseThrow(()->new NotFoundException(NOT_FOUND));

        Assignment assignmentExist=assignmentRepository.findByTitleAndAssignmentLink(assignmentDto.getTitle(),assignmentDto.getAssignmentLink())
                .orElse(null);
        if(!Objects.isNull(assignmentExist)){
            throw new AlreadyExistException(EXIST);
        }

        Assignment assignment=new Assignment();
        assignment.setAssignmentLink(assignmentDto.getAssignmentLink());
        assignment.setTitle(assignmentDto.getTitle());
        assignment.setCourse(course);
        assignment.setDueDate(assignmentDto.getDueDate());
        assignmentRepository.save(assignment);
        return new SingleResponse(CREATED);
    }

    public List<AssignmentDto> getAssignment(Long courseId) {

        List<Assignment>assignments=assignmentRepository.findByCourse_CourseId(courseId);
        return assignments.stream().map(assignmentMapper::toDto).toList();
    }

    public SingleResponse updateAssignment(Long assignmentId, AssignmentUpdateRequest updates) {
        Assignment assignment=assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new NotFoundException(NOT_FOUND));

        if(!Objects.isNull(updates.getAssignmentLink())){
            assignment.setAssignmentLink(updates.getAssignmentLink());
        }
        if(!Objects.isNull(updates.getTitle())){
            assignment.setTitle(updates.getTitle());
        }
        if(!Objects.isNull(updates.getDueDate())){
            assignment.setDueDate(updates.getDueDate());
        }

        assignmentRepository.save(assignment);
        return new SingleResponse(UPDATED);
    }

    public SingleResponse deleteAssignment(Long assignmentId) {
        Assignment assignment=assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new NotFoundException(NOT_FOUND));

        assignmentRepository.delete(assignment);
        return new SingleResponse(DELETED);

    }
}
