package com.example.JSS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "approvals")
public class Approvals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approvalId;
    private Long applicationId;
    private LocalDateTime approvalDate;
    private boolean status;
}
