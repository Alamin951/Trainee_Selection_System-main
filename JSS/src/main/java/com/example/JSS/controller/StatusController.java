package com.example.JSS.controller;

import com.example.JSS.dto.StatusResponseDto;
import com.example.JSS.entity.Status;
import com.example.JSS.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/status")
public class StatusController {
    private final StatusService statusService;

    @PostMapping
    public ResponseEntity<?> createStatus(@RequestBody Status status){
        return ResponseEntity.status(HttpStatus.CREATED).body(statusService.create(status));
    }
    @GetMapping("/{status}")
    public ResponseEntity<Status> getStatus(@PathVariable("status") String status){
        return new ResponseEntity<>(statusService.getStatusByStatusName(status), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StatusResponseDto>> getAllStatus(){
        return new  ResponseEntity<>(statusService.getAll(),HttpStatus.OK);
    }
}
