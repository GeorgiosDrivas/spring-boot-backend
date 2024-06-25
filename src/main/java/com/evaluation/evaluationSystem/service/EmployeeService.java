package com.evaluation.evaluationSystem.service;

import com.evaluation.evaluationSystem.model.Employee;
import com.evaluation.evaluationSystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee registerUser(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getUserById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public Employee loginUser(String email, String password) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee != null && employee.getPassword().equals(password)) {
            return employee;
        }
        return null;
    }
}
