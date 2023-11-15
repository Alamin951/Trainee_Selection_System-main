package com.example.JSS.controller;

import com.example.JSS.dto.JobCircularDto;
import com.example.JSS.entity.JobCircular;
import com.example.JSS.service.JobCircularService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job_circular")
@RequiredArgsConstructor
public class JobCircularController {
    private final JobCircularService jobCircularService;

    @PostMapping
    public ResponseEntity<JobCircular> createJobCircular(@RequestBody JobCircularDto jobCircularDto){
        JobCircular jobCircular = jobCircularService.createJobCircular(jobCircularDto);
        return new ResponseEntity<>(jobCircular, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JobCircular> > getAllJobCircular(){
        List<JobCircular> jobCirculars= jobCircularService.getAllCircular();
        return new ResponseEntity<>(jobCirculars, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    ResponseEntity<JobCircular> getCircularById(@PathVariable("id") Long circularId){
        JobCircular jobCircular = jobCircularService.getCircularById(circularId);
        return new ResponseEntity<>(jobCircular, HttpStatus.OK);
    }

    @PutMapping("/{circularId}")
    public ResponseEntity<JobCircular> editJobCircular(
            @PathVariable("circularId") Long circularId,
            @RequestBody JobCircularDto jobCircularDTO) {
        JobCircular jobCircular = jobCircularService.updateJobCircular(circularId, jobCircularDTO);
        return new ResponseEntity<>(jobCircular, HttpStatus.OK);
    }
    }

