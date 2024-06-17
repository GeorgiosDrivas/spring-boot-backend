package com.evaluation.evaluationSystem.repository;

import com.evaluation.evaluationSystem.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u FROM Users u WHERE u.email LIKE %:word%")
    List<Users> getContainingQuote(@Param("word") String word);
}