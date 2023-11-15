package com.example.JSS.service.impl;

import com.example.JSS.dto.PendingApprovalDto;
import com.example.JSS.entity.Approvals;
import com.example.JSS.repository.ApprovalRepository;
import com.example.JSS.repository.marking.AnswerSheetRepository;
import com.example.JSS.service.AdmitCardService;
import com.example.JSS.service.ApplicationsService;
import com.example.JSS.service.ApprovalService;
import com.example.JSS.service.marking.AnswerSheetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {
    private final ApprovalRepository approvalRepository;
    private final AnswerSheetService answerSheetService;
    private final AdmitCardService admitCardService;

    @Override
    public void createApproval(Long applicationId, LocalDateTime approvalTime) {

        Optional<Approvals> existingApproval = approvalRepository.findByApplicationId(applicationId);
        existingApproval.ifPresent(approvals -> approvalRepository.deleteById(approvals.getApprovalId()));

        Approvals approvals = new Approvals();
        approvals.setApplicationId(applicationId);
        approvals.setApprovalDate(approvalTime);
        approvalRepository.save(approvals);
    }

    @Override
    public void removeApproval(Long applicationId) {
        Optional<Approvals> approvals= approvalRepository.findByApplicationId(applicationId);
        approvalRepository.deleteById(approvals.get().getApprovalId());

    }

    @Override
    @Transactional
    public void acceptApproval(Long approvalId) {
        try {
            Approvals approvals = approvalRepository.findById(approvalId)
                    .orElseThrow(()-> new EntityNotFoundException("INVALID_APPROVAL"));
            approvals.setStatus(true);

            // calling answerSheet service to generate the participation code
            answerSheetService.createAnswerSheetByApplicationID(approvals.getApplicationId());
            approvalRepository.save(approvals);
            admitCardService.generateAdmitCard(approvals.getApplicationId());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while accepting the approval.", e);
        }
    }

    @Override
    public List<PendingApprovalDto> getPendingApproval(List<Long> applicationId){
        List<Approvals> approvalsList = approvalRepository.findByApplicationIdInAndStatus(applicationId, false);

        return approvalsList.stream()
                .map(approval -> new PendingApprovalDto(approval.getApprovalId(), approval.getApplicationId()))
                .collect(Collectors.toList());
    }


}
