package com.example.JSS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicantsDto {
    private Long applicantId;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dateOfBirth;
    private String email;
    private String password; //password for register
    private String contactNumber;
    private String degreeName;
    private String educationalInstitute;
    private double cgpa;
    private int passingYear;
    private String presentAddress;
    private String photoUrl;
    private String resumeUrl;
}
