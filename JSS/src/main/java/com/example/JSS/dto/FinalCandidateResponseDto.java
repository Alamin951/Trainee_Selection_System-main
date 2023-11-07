package com.example.JSS.dto;

import com.example.JSS.entity.Applicants;
import com.example.JSS.entity.JobCircular;
import com.example.JSS.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinalCandidateResponseDto {
    private Long applicationId;
    private Applicants applicants;
    private JobCircular jobCircular;
    private Date appliedDate;
    private Status status;
    private float marks;
}
