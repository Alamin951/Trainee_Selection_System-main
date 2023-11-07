package com.example.JSS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "applications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Applications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;
    private Long applicantId;
    @ManyToOne
    @JoinColumn(name = "circular_id")
    private JobCircular jobCircular;
    private Date appliedDate;
    @OneToOne
    private Status status;
}
