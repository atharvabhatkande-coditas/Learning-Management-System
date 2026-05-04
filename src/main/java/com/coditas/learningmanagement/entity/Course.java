package com.coditas.learningmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    private String title;
    private String description;
    private String technology;
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Employee createdBy;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    private List<Enrollment> enrollmentList=new ArrayList<>();

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    private List<Lectures>lectures=new ArrayList<>();

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    private List<Assignment>assignments=new ArrayList<>();



}
