package com.example.JSS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendingApprovalResponseDto {
    private Long approvalId;
    private Long applicationId;
    private String circularName;
}
