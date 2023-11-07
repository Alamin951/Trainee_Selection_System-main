package com.example.JSS.service;

import com.example.JSS.dto.ApplicationsDto;
import com.example.JSS.dto.FinalCandidateResponseDto;
import com.example.JSS.dto.PendingApprovalDto;
import com.example.JSS.dto.PendingApprovalResponseDto;
import com.example.JSS.entity.Applicants;
import com.example.JSS.entity.ApplicationResponseAdminDto;
import com.example.JSS.entity.Applications;

import java.util.List;

public interface ApplicationsService {
    Applications createApplication(ApplicationsDto applicationsDto);
    Applications updateApplications(Long applicationId, String status);
    String deleteApplication(Long applicationId);

    List<Applications> getApplicantById(Long applicantId);

    List<Applications> getAllApplications();
    List<ApplicationResponseAdminDto> getAllApplicationsDetails();
    Long getApplicantId(Long applicationId);

    List<PendingApprovalResponseDto> getPendingApprovalApplicant(Long applicantId);
    List<FinalCandidateResponseDto> getAllSelectedCandidate();


}
