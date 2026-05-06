package com.coditas.learningmanagement.service;

import com.coditas.learningmanagement.dto.AssignmentDto;
import com.coditas.learningmanagement.dto.response.GeneralResponse;
import com.coditas.learningmanagement.entity.Assignment;
import com.coditas.learningmanagement.entity.Course;

import com.coditas.learningmanagement.exception.AlreadyExistException;
import com.coditas.learningmanagement.exception.NotFoundException;
import com.coditas.learningmanagement.mappers.AssignmentMapper;
import com.coditas.learningmanagement.repository.AssignmentRepository;
import com.coditas.learningmanagement.repository.CourseRepository;
import com.coditas.learningmanagement.repository.CustomUserDetailsRepository;
import com.coditas.learningmanagement.repository.EnrollmentRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.coditas.learningmanagement.constants.DtoConstants.*;



@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final AssignmentMapper assignmentMapper;
    private final ObjectMapper objectMapper;
    private final CustomUserDetailsRepository customUserDetailsRepository;
    private final AuthService authService;
    private final EnrollmentRepository enrollmentRepository;

    public GeneralResponse createAssignment(AssignmentDto assignmentDto,Long courseId) {
        Course course=courseRepository.findById(courseId).orElseThrow(()->new NotFoundException(NOT_FOUND));
        Assignment assignmentExist=assignmentRepository.findByTitleAndAssignmentLink(assignmentDto.getTitle(),assignmentDto.getAssignmentLink())
                .orElse(null);
        if(assignmentExist!=null){
            throw new AlreadyExistException(EXIST);
        }

        Assignment assignment=new Assignment();
        assignment.setAssignmentLink(assignmentDto.getAssignmentLink());
        assignment.setTitle(assignmentDto.getTitle());
        assignment.setCourse(course);
        assignment.setDueDate(assignmentDto.getDueDate());
        assignmentRepository.save(assignment);
        return new GeneralResponse(CREATED);
    }

    public List<AssignmentDto> getAssignment(Long courseId) {

        List<Assignment>assignments=assignmentRepository.findByCourse_CourseId(courseId);
        return assignments.stream().map(assignmentMapper::toDto).toList();
    }

    public GeneralResponse updateAssignment(Long assignmentId, @Valid Map<String, Object> updates) {
        Assignment assignment=assignmentRepository.findById(assignmentId).orElseThrow(()->new NotFoundException(NOT_FOUND));
        try {
            objectMapper.updateValue(assignment,updates);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        }

        assignmentRepository.save(assignment);
        return new GeneralResponse(UPDATED);
    }

    public GeneralResponse deleteAssignment(Long assignmentId) {
        Assignment assignment=assignmentRepository.findById(assignmentId).orElseThrow(()->new NotFoundException(NOT_FOUND));
        assignmentRepository.delete(assignment);
        return new GeneralResponse(DELETED);

    }
}
