package com.example.JSS.service.impl;

import com.example.JSS.dto.ApplicationsDto;
import com.example.JSS.dto.FinalCandidateResponseDto;
import com.example.JSS.dto.PendingApprovalDto;
import com.example.JSS.dto.PendingApprovalResponseDto;
import com.example.JSS.entity.*;
import com.example.JSS.repository.ApplicantsRepository;
import com.example.JSS.repository.ApplicationsRepository;
import com.example.JSS.repository.JobCircularRepository;
import com.example.JSS.repository.marking.MarksRepository;
import com.example.JSS.service.ApplicationsService;
import com.example.JSS.service.ApprovalService;
import com.example.JSS.service.NoticeService;
import com.example.JSS.service.StatusService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ApplicationsServiceImpl implements ApplicationsService {
    private final ApplicationsRepository applicationsRepository;
    private final JobCircularRepository jobCircularRepository;
    private final ApplicantsRepository applicantsRepository;
    private final StatusService statusService;
    private final ModelMapper modelMapper;
    private final ApprovalService approvalService;
    private final MarksRepository marksRepository;
    private final NoticeService noticeService;

    @Override
    public Applications createApplication(ApplicationsDto applicationsDto) {
        JobCircular jobCircular = jobCircularRepository.findById(applicationsDto.getCircularId())
                .orElseThrow(()-> new IllegalArgumentException("Invalid Job Circular!!!"));

        Optional<Applications> existingApplications= applicationsRepository
                .findByJobCircularCircularIdAndApplicantId(applicationsDto.getCircularId(), applicationsDto.getApplicantId());
        if(existingApplications.isPresent()){
            throw new IllegalArgumentException("Already applied!!!");
        }
        Status status =  statusService.getStatusByStatusName("Applicant");
        Applications applications= modelMapper.map(applicationsDto, Applications.class);
        applications.setStatus(status);
        applications.setAppliedDate(new Date());
        applications.setJobCircular(jobCircular);

        return applicationsRepository.save(applications);
    }

    @Override
    public Applications updateApplications(Long applicationId, String status) {
        Applications application= applicationsRepository.findById(applicationId)
                .orElseThrow(()-> new IllegalArgumentException("Application not Available!!!"));
        Status previousStatus = application.getStatus();
        Status newStatus = statusService.getStatusByStatusName(status);
        application.setStatus(newStatus);
        // send notification
        noticeService.createNotice(application, "You have been selected for "+status);
        Applications updatedApplication = applicationsRepository.save(application);

        if (!Objects.equals(previousStatus.getStatusId(), newStatus.getStatusId())) {
            if (newStatus.getStatusId() == 2) {
                LocalDateTime approvalTime = LocalDateTime.now();
                approvalService.createApproval(applicationId, approvalTime);
            } else if (newStatus.getStatusId() == 1) {
                approvalService.removeApproval(applicationId);
            }
        }

        return updatedApplication;
    }

    @Override
    public String deleteApplication(Long applicationId) {
        applicationsRepository.deleteById(applicationId);
        return "Deleted successfully!!";
    }

    @Override
    public List<Applications> getApplicantById(Long applicantId) {
        List<Applications> applications= applicationsRepository.findByApplicantId(applicantId)
                .orElseThrow(()-> new IllegalArgumentException("NOT AVAILABLE!!!"));
        if(applications.isEmpty()){
            throw new IllegalArgumentException("No application Available!!!");
        }
        return applications;

    }

    @Override
    public List<Applications> getAllApplications() {
        return applicationsRepository.findAll();
    }

    @Override
    public List<ApplicationResponseAdminDto> getAllApplicationsDetails() {
        List<Applications> applicationsList = getAllApplications();
        List<ApplicationResponseAdminDto> applicationResponseAdminDtos = new ArrayList<>();

        for (Applications application : applicationsList) {
            Long applicantId = application.getApplicantId();
            Applicants applicants = getApplicantDetailsById(applicantId);

            ApplicationResponseAdminDto dto = new ApplicationResponseAdminDto();
            dto.setApplicationId(application.getApplicationId());
            dto.setApplicants(applicants);
            dto.setJobCircular(application.getJobCircular());
            dto.setAppliedDate(application.getAppliedDate());
            dto.setStatus(application.getStatus());

            applicationResponseAdminDtos.add(dto);
        }

        return applicationResponseAdminDtos;
    }


    @Override
    public Long getApplicantId(Long applicationId) {
        Applications applications =applicationsRepository.findById(applicationId)
                .orElseThrow(()-> new EntityNotFoundException("Application not available!!!"));
        return applications.getApplicantId();
    }

    @Override
    public List<PendingApprovalResponseDto> getPendingApprovalApplicant(Long applicantId) {
        List<Applications> applicationsList = getApplicantById(applicantId);

        List<Long> applicationIds = applicationsList.stream()
                .map(Applications::getApplicationId)
                .collect(Collectors.toList());
        List<PendingApprovalDto> pendingApprovalDtos = approvalService.getPendingApproval(applicationIds);

        List<PendingApprovalResponseDto> pendingApprovalResponseDtoList = pendingApprovalDtos.stream()
                .map(pendingApprovalDto -> {
                    // Fetch the Applications entity by the applicationId from the PendingApprovalDto
                    Applications applications = applicationsRepository.findById(pendingApprovalDto.getApplicationId())
                            .orElseThrow(() -> new EntityNotFoundException("Application not found for ID: " + pendingApprovalDto.getApplicationId()));

                    // Extract the JobCircularName from the fetched Applications entity
                    String jobCircularName = applications.getJobCircular().getCircularName();

                    // Create the PendingApprovalResponseDto with the required fields
                    return new PendingApprovalResponseDto(
                            pendingApprovalDto.getApprovalId(),
                            pendingApprovalDto.getApplicationId(),
                            jobCircularName
                    );
                })
                .collect(Collectors.toList());

        return pendingApprovalResponseDtoList;
    }

    @Override
    public List<FinalCandidateResponseDto> getAllSelectedCandidate() {
        List<Applications> applicationsList = applicationsRepository.findByStatusStatus("Trainee");
        List<FinalCandidateResponseDto> finalCandidateResponseDtoList = new ArrayList<>();
        for(Applications application : applicationsList){
            Long applicantId =  application.getApplicantId();
            Applicants applicants = getApplicantDetailsById(applicantId);
            FinalCandidateResponseDto dto = new FinalCandidateResponseDto();
            dto.setApplicationId(application.getApplicationId());
            dto.setApplicants(applicants);
            dto.setJobCircular(application.getJobCircular());
            dto.setStatus(application.getStatus());
            float totalMarks = 0.0f;
            List<Marks> marksList = marksRepository.findByApplicationsApplicationId(application.getApplicationId())
                    .orElseThrow(()->  new EntityNotFoundException("NOT_FOUND"));
            for (Marks marks : marksList) {
                totalMarks += marks.getMarks();
            }
            dto.setMarks(totalMarks);
            finalCandidateResponseDtoList.add(dto);
        }

        return finalCandidateResponseDtoList;
    }

    public Applicants getApplicantDetailsById(Long applicantId) {
        return applicantsRepository.findById(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found for ID: " + applicantId));
    }
}
