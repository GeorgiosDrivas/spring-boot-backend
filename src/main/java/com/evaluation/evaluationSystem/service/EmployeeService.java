package com.evaluation.evaluationSystem.service;

import com.evaluation.evaluationSystem.model.Employee;
import com.evaluation.evaluationSystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee registerUser(Employee employee) {
        logger.info("Registering employee: {}", employee.getEmail());
        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            logger.error("Error registering employee", e);
            throw e;
        }
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

    public Employee updateUserProfile(Long employeeId, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setLocation(updatedEmployee.getLocation());
        existingEmployee.setCurrentEmployer(updatedEmployee.getCurrentEmployer());

        return employeeRepository.save(existingEmployee);
    }
}
