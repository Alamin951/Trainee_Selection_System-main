package com.example.JSS.dto;

import com.example.JSS.entity.Applicants;
import com.example.JSS.entity.Applications;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmitCardResponseDto {
    private Applications applications;
    private Applicants applicants;
    private String qrCode;
    private LocalDateTime expiringDate;
}
