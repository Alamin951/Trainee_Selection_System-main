package com.example.JSS.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Entity
@Table(
        name = "applicants",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "applicant_email_unique",
                        columnNames = "email"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Applicants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicantId;
    private String firstName;
    private String lastName;
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date dateOfBirth;
    private String email;
    private String contactNumber;
    private String degreeName;
    private String educationalInstitute;
    private double cgpa;
    private int passingYear;
    private String presentAddress;
    private String photoUrl;
    private String resumeUrl;
}
