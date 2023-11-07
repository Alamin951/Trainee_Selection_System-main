package com.example.JSS.repository;

import com.example.JSS.entity.Applications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationsRepository extends JpaRepository<Applications,Long> {
    Optional<List<Applications>> findByApplicantId(Long applicntId);
    Optional<Applications> findByJobCircularCircularIdAndApplicantId(Long circularId, Long applicantId);
    List<Applications> findByStatusStatus(String status);
}
