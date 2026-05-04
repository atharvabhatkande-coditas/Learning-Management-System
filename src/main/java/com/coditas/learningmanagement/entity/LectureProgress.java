package com.coditas.learningmanagement.entity;

import com.coditas.learningmanagement.enums.LectureStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LectureProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long progressId;

    @Enumerated(EnumType.STRING)
    private LectureStatus lectureStatus;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lectures lecture;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
