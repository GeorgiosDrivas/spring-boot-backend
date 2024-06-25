package com.evaluation.evaluationSystem.controller;

import com.evaluation.evaluationSystem.model.Employee;
import com.evaluation.evaluationSystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public Employee registerEmployee(@RequestBody Employee employee) {
        return employeeService.registerUser(employee);
    }

    @PostMapping("/login")
    public Employee loginEmployee(@RequestBody Employee employee) {
        return employeeService.loginUser(employee.getEmail(), employee.getPassword());
    }
}
