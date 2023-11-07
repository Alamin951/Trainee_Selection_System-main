package com.example.JSS.service;

import com.example.JSS.dto.PendingApprovalDto;
import com.example.JSS.entity.Approvals;

import java.time.LocalDateTime;
import java.util.List;

public interface ApprovalService {
    void createApproval(Long applicationId, LocalDateTime approvalTime);
    void removeApproval(Long applicationId);
    void acceptApproval(Long approvalId);
    public List<PendingApprovalDto> getPendingApproval(List<Long> applicationId);

}
