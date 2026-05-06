package com.coditas.learningmanagement.entity;

import com.coditas.learningmanagement.enums.ContentType;
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
public class Lectures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;
    private String title;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private String lectureLink;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "lecture")
    private List<LectureProgress> lectureProgressList=new ArrayList<>();



}
