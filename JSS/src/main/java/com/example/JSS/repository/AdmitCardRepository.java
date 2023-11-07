package com.example.JSS.repository;

import com.example.JSS.entity.AdmitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdmitCardRepository extends JpaRepository<AdmitCard,Long> {
    List<AdmitCard> findByApplicationsApplicantIdAndExpiringDateAfter(Long applicantId, LocalDateTime currentDateTime);
}
