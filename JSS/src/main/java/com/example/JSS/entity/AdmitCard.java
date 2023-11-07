package com.example.JSS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdmitCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AdmitCardId;
    @OneToOne
    private Applications applications;
    @Column(length = 2000)
    private String qrCode;
    private LocalDateTime expiringDate;

}
