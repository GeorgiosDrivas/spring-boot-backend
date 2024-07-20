package com.evaluation.evaluationSystem.service;

import com.evaluation.evaluationSystem.model.Employer;
import com.evaluation.evaluationSystem.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

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

        if (updatedEmployer.getProfileImagePath() != null) {
            existingEmployer.setProfileImagePath(updatedEmployer.getProfileImagePath());
        }

        return employerRepository.save(existingEmployer);
    }


    public void saveProfileImage(Long employerId, MultipartFile file) throws IOException {
        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Create a safe filename using employeeId and the original filename
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new IOException("Invalid file name");
        }
        String fileName = employerId + "_" + originalFileName;
        Path filePath = uploadPath.resolve(fileName);

        try {
            // Copy the file to the specified path
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error("Failed to save file: " + fileName, e);
            throw e;
        }

        // Update the employee profile with the filename
        Employer employer = getUserById(employerId);
        employer.setProfileImagePath(fileName); // Save only the filename
        updateUserProfile(employerId, employer);
    }
}
