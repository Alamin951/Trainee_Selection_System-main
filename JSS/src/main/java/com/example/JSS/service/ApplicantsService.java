package com.example.JSS.service;

import com.example.JSS.dto.ApplicantsDto;
import com.example.JSS.dto.PendingApprovalDto;
import com.example.JSS.dto.UsersDto;


import java.util.List;
import java.util.Optional;

public interface ApplicantsService {
    List<ApplicantsDto> getAllApplicants();
    Optional<ApplicantsDto> getApplicantById(Long applicantId);
    ApplicantsDto createApplicant(ApplicantsDto applicantsDto);
    ApplicantsDto updateApplicant(Long applicantId, ApplicantsDto applicantsDto);
    void deleteApplicant(Long applicantId);


}
