package com.evaluation.evaluationSystem.service;

import com.evaluation.evaluationSystem.model.Employee;
import com.evaluation.evaluationSystem.model.Evaluation;
import com.evaluation.evaluationSystem.repository.EmployeeRepository;
import com.evaluation.evaluationSystem.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EvaluationRepository evaluationRepository) {
        this.employeeRepository = employeeRepository;
        this.evaluationRepository = evaluationRepository;
    }

    public Employee registerUser(Employee employee) {
        logger.info("Registering employee: {}", employee.getEmail());
        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            logger.error("Error registering employee", e);
            throw e;
        }
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    public Employee getUserById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public Employee loginUser(String email, String password) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee != null && employee.getPassword().equals(password)) {
            return employee;
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }

    public Employee updateUserProfile(Long employeeId, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setLocation(updatedEmployee.getLocation());
        existingEmployee.setTitle(updatedEmployee.getTitle());
        existingEmployee.setCurrentEmployer(updatedEmployee.getCurrentEmployer());

        return employeeRepository.save(existingEmployee);
    }

    public Evaluation addEvaluation(Long employeeId, Evaluation evaluationData) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Evaluation evaluation = new Evaluation();
        evaluation.setTitle(evaluationData.getTitle());
        evaluation.setContent(evaluationData.getContent());
        evaluation.setEmployerName(evaluationData.getEmployerName());
        evaluation.setEmployee(existingEmployee);

        return evaluationRepository.save(evaluation);
    }

    public List<Evaluation> getEmployeeEvaluations(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return employee.getEvaluations();
    }
}