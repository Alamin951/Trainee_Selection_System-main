package com.example.JSS.controller;

import com.example.JSS.dto.ApplicantsDto;
import com.example.JSS.dto.UsersDto;
import com.example.JSS.model.EvaluatorResponse;
import com.example.JSS.model.RegisterResponse;
import com.example.JSS.service.EvaluatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluator")
public class EvaluatorController {
    private final EvaluatorService evaluatorService;
    @PostMapping
    public ResponseEntity<?> createApplicants(@RequestBody UsersDto usersDto){
        RegisterResponse createEvaluator = evaluatorService.createUsers(usersDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createEvaluator);
    }
    @GetMapping
    public List<EvaluatorResponse> getAllEvaluators() {
        return evaluatorService.getAllEvaluator();
    }
}
