package com.example.JSS.service;

import com.example.JSS.dto.JobCircularDto;
import com.example.JSS.entity.Applications;
import com.example.JSS.entity.JobCircular;

import java.util.List;

public interface JobCircularService {
    JobCircular createJobCircular(JobCircularDto jobCircularDto);
    JobCircular updateJobCircular(Long circularId, JobCircularDto jobCircularDto);
    JobCircular getCircularById(Long circularId);

    List<JobCircular> getAllCircular();

}
