package com.evaluation.evaluationSystem.controller;

import com.evaluation.evaluationSystem.model.Employee;
import com.evaluation.evaluationSystem.model.Evaluation;
import com.evaluation.evaluationSystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:5173")

public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    public Employee displayEmployee(@PathVariable Long employeeId) {
        return employeeService.getUserById(employeeId);
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/register")
    public Employee registerEmployee(@RequestBody Employee employee) {
        return employeeService.registerUser(employee);
    }

    @PostMapping("/login")
    public Employee loginEmployee(@RequestBody Employee employee) {
        return employeeService.loginUser(employee.getEmail(), employee.getPassword());
    }

    @PutMapping("/{employeeId}/profile")
    public Employee updateEmployeeProfile(@PathVariable Long employeeId, @RequestBody Employee employee) {
        return employeeService.updateUserProfile(employeeId, employee);
    }

    @PostMapping("/{employeeId}/add-evaluations")
    public Employee addEvaluation(@PathVariable Long employeeId, @RequestBody Evaluation evaluation) {
        return employeeService.addEvaluation(employeeId, evaluation).getEmployee();
    }

    @GetMapping("/{employeeId}/evaluations")
    public List<Evaluation> displayEmployeeEvaluations(@PathVariable Long employeeId) {
        return employeeService.getEmployeeEvaluations(employeeId);
    }

    @PostMapping("/{employeeId}/image")
    public ResponseEntity<String> uploadProfileImage(@PathVariable Long employeeId, @RequestParam("imageFile") MultipartFile file) {
        try {
            employeeService.saveProfileImage(employeeId, file);
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
        }
    }
}
