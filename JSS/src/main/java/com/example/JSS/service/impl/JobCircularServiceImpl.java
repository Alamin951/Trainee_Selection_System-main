package com.example.JSS.service.impl;

import com.example.JSS.dto.JobCircularDto;
import com.example.JSS.entity.JobCircular;
import com.example.JSS.repository.JobCircularRepository;
import com.example.JSS.service.JobCircularService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobCircularServiceImpl implements JobCircularService {
    private final JobCircularRepository jobCircularRepository;
    private final ModelMapper modelMapper;

    @Override
    public JobCircular createJobCircular(JobCircularDto jobCircularDto) {
        JobCircular jobCircular = modelMapper.map(jobCircularDto, JobCircular.class);
        return jobCircularRepository.save(jobCircular);
    }

    @Override
    public JobCircular updateJobCircular(Long circularId, JobCircularDto jobCircularDto) {
        JobCircular jobCircular = jobCircularRepository.findById(circularId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid circular ID"));

        JobCircular updateJobCircular= modelMapper.map(jobCircularDto, JobCircular.class);
        return jobCircularRepository.save(updateJobCircular);
    }

    @Override
    public JobCircular getCircularById(Long circularId) {
        return jobCircularRepository.findById(circularId).orElseThrow(()-> new IllegalArgumentException("Invalid Circular!!!"));
    }

    @Override
    public List<JobCircular> getAllCircular() {
        return jobCircularRepository.findAll();
    }
}
