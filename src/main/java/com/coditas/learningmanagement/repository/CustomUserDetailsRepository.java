package com.coditas.learningmanagement.repository;

import com.coditas.learningmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomUserDetailsRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByUsername(String username);
}
