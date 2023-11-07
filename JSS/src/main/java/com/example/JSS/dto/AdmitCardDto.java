package com.example.JSS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmitCardDto {
    private Long admitCardId;
    private Long approvalId;
    private Long applicantId;
    private String applicantName;
    private String phoneNumber;
    private String qrCode;
}
