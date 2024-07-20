package com.evaluation.evaluationSystem.controller;

import com.evaluation.evaluationSystem.model.Employee;
import com.evaluation.evaluationSystem.model.Employer;
import com.evaluation.evaluationSystem.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PutMapping("/{employerId}/profile")
    public Employer updateEmployerProfile(@PathVariable Long employerId, @RequestBody Employer employer) {
        return employerService.updateUserProfile(employerId, employer);
    }

    @PostMapping("/{employerId}/image")
    public ResponseEntity<String> uploadProfileImage(@PathVariable Long employerId, @RequestParam("imageFile") MultipartFile file) {
        try {
            employerService.saveProfileImage(employerId, file);
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
        }
    }
}
