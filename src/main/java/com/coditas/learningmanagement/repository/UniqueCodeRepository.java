package com.coditas.learningmanagement.repository;

import com.coditas.learningmanagement.entity.UniqueCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniqueCodeRepository extends JpaRepository<UniqueCode,LectureRepository> {
    Optional<UniqueCode> findByEmail(String recipient);
}
