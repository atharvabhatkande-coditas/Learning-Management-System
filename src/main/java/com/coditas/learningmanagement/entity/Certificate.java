package com.coditas.learningmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateId;

    private LocalDate issuedAt;
    private String certificateUrl;


    @ManyToOne
    @JoinColumn(name = "issued_to")
    private Employee issuedTo;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
