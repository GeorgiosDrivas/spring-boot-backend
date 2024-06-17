package com.evaluation.evaluationSystem.controller;

import com.evaluation.evaluationSystem.model.Users;
import com.evaluation.evaluationSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsersController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<Users> getUsers(@RequestParam("search") Optional<String> searchParam){
        return searchParam.map(param -> userRepository.getContainingQuote(param))
                .orElse(userRepository.findAll());
    }
}
