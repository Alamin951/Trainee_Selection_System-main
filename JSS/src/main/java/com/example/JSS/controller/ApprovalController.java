package com.example.JSS.controller;

import com.example.JSS.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/approval")
public class ApprovalController {
    private final ApprovalService approvalService;
    @PatchMapping("/pending/{approvalId}")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable("approvalId") Long approvalId) {
        approvalService.acceptApproval(approvalId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
