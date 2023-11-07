package com.example.JSS.service.impl;

import com.example.JSS.dto.StatusResponseDto;
import com.example.JSS.entity.Status;
import com.example.JSS.repository.StatusRepository;
import com.example.JSS.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;
    private final ModelMapper modelMapper;
    @Override
    public Status create(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public Status getStatusByStatusName(String status) {
        return statusRepository.findByStatus(status).orElseThrow(()-> new IllegalArgumentException("INVALID_STATUS!!"));
    }

    @Override
    public List<StatusResponseDto> getAll() {
        List<Status> statusList= statusRepository.findAll();
        return statusList.stream()
                .map(status -> modelMapper.map(status, StatusResponseDto.class))
                .collect(Collectors.toList());
    }
}
