package com.coditas.learningmanagement.repository;

import com.coditas.learningmanagement.entity.Lectures;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lectures,Long> {
}
