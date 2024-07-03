package com.evaluation.evaluationSystem.service;

import com.evaluation.evaluationSystem.model.Employee;
import com.evaluation.evaluationSystem.model.Employer;
import com.evaluation.evaluationSystem.repository.EmployeeRepository;
import com.evaluation.evaluationSystem.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;

    public Employer registerUser(Employer employer) {
        return employerRepository.save(employer);
    }

    public Employer getUserById(Long employerId) {
        return employerRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer not found"));
    }

    public Employer loginUser(String email, String password) {
        Employer employer = employerRepository.findByEmail(email);
        if (employer != null && employer.getPassword().equals(password)) {
            return employer;
        } else {
            // Handle incorrect credentials
            throw new RuntimeException("Invalid email or password");
        }
    }

    public Employer updateUserProfile(Long employerId, Employer updatedEmployer) {
        Employer existingEmployer = employerRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existingEmployer.setCompanyName(updatedEmployer.getCompanyName());
        existingEmployer.setField(updatedEmployer.getField());
        existingEmployer.setLocation(updatedEmployer.getLocation());

        return employerRepository.save(existingEmployer);
    }
}
