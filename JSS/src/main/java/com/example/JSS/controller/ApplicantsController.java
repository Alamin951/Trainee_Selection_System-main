package com.example.JSS.controller;

import com.example.JSS.dto.ApplicantsDto;
import com.example.JSS.service.ApplicantsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/applicants")
public class ApplicantsController {
    private final ApplicantsService applicantsService;

    @PostMapping("/register")
    public ResponseEntity<ApplicantsDto> createApplicants(@RequestBody ApplicantsDto applicantsDto){
        ApplicantsDto createApplicant = applicantsService.createApplicant(applicantsDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createApplicant);
    }
    @GetMapping
    public ResponseEntity<List<ApplicantsDto>> getAllApplicants() {
        List<ApplicantsDto> applicants = applicantsService.getAllApplicants();
        return ResponseEntity.ok(applicants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicantsDto> getApplicantById(@PathVariable("id") Long id) {
        Optional<ApplicantsDto> optionalApplicant = applicantsService.getApplicantById(id);
        return optionalApplicant
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicantsDto> updateApplicant(@PathVariable("id") Long id, @RequestBody ApplicantsDto applicantsDto) {
        ApplicantsDto updatedApplicant = applicantsService.updateApplicant(id, applicantsDto);
        return ResponseEntity.ok(updatedApplicant);
    }


}
