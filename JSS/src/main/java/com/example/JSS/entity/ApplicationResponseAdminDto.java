package com.example.JSS.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponseAdminDto {
    private Long applicationId;
    private Applicants applicants;
    private JobCircular jobCircular;
    private Date appliedDate;
    private Status status;
}
