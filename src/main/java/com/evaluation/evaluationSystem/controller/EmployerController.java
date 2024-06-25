package com.evaluation.evaluationSystem.controller;

import com.evaluation.evaluationSystem.model.Employer;
import com.evaluation.evaluationSystem.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employers")
@CrossOrigin(origins = "http://localhost:5173")

public class EmployerController {
    @Autowired
    private EmployerService employerService;

    @GetMapping("/{employerId}")
    public Employer displayEmployer(@PathVariable Long employerId) {
        return employerService.getUserById(employerId);
    }

    @PostMapping("/register")
    public Employer registerEmployer(@RequestBody Employer employer) {
        return employerService.registerUser(employer);
    }

    @PostMapping("/login")
    public Employer loginEmployer(@RequestBody Employer employer) {
        return employerService.loginUser(employer.getEmail(), employer.getPassword());
    }
}
