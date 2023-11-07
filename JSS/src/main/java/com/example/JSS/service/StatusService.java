package com.example.JSS.service;

import com.example.JSS.dto.StatusResponseDto;
import com.example.JSS.entity.Status;

import java.util.List;

public interface StatusService {
    Status create(Status status);
    Status getStatusByStatusName(String status);
    List<StatusResponseDto> getAll();

}
