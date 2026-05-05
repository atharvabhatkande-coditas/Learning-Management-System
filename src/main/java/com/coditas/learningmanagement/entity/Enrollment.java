package com.coditas.learningmanagement.entity;

import com.coditas.learningmanagement.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus enrollmentStatus;

    @CreationTimestamp
    private LocalDate startDate;
    private LocalDate endDate;
    private Double progress;

    @ManyToOne
    @JoinColumn(name = "enrolled_by")
    private Employee enrolledBy;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
