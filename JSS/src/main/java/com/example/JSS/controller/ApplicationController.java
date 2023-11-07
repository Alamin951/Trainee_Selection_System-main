package com.example.JSS.controller;

import com.example.JSS.dto.*;
import com.example.JSS.entity.ApplicationResponseAdminDto;
import com.example.JSS.entity.Applications;
import com.example.JSS.entity.JobCircular;
import com.example.JSS.service.ApplicationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationsService applicationsService;

    @PostMapping
    public ResponseEntity<Applications> createApplication(@RequestBody ApplicationsDto applicationDTO) {
        Applications application = applicationsService.createApplication(applicationDTO);
        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Applications>> getAllApplications(){
        List<Applications> applications= applicationsService.getAllApplications();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<ApplicationResponseAdminDto>> getAllApplicationsDetails(){
        List<ApplicationResponseAdminDto> applications= applicationsService.getAllApplicationsDetails();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }
    @GetMapping("/applicant/{id}")
    ResponseEntity<List<Applications>> getApplicationByApplicantId(@PathVariable("id") Long applicationId){
        List<Applications> applications = applicationsService.getApplicantById(applicationId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @PatchMapping("/update-status")
    public ResponseEntity<Applications> updateApplicationStatus(@RequestBody StatusUpdateDto statusUpdateDto) {
        Applications application = applicationsService.updateApplications(statusUpdateDto.getApplicationId(), statusUpdateDto.getStatus());
        return new ResponseEntity<>(application, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable("id") Long id) {
        applicationsService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/pending/{id}")
    ResponseEntity<List<PendingApprovalResponseDto>> getPendingApplicationByApplicantId(@PathVariable("id") Long applicantId){
        List<PendingApprovalResponseDto> pendingApprovalDtos = applicationsService.getPendingApprovalApplicant(applicantId);
        return new ResponseEntity<>(pendingApprovalDtos, HttpStatus.OK);
    }
    @GetMapping("/final-candidate")
    public ResponseEntity<List<FinalCandidateResponseDto>> getAllFinalCandidate(){
        List<FinalCandidateResponseDto> dtoList = applicationsService.getAllSelectedCandidate();
        return ResponseEntity.ok(dtoList);
    }

}
