package com.evaluation.evaluationSystem.repository;

import com.evaluation.evaluationSystem.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Employer findByEmail(String email);
}