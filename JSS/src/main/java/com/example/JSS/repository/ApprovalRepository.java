package com.example.JSS.repository;

import com.example.JSS.entity.Approvals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalRepository extends JpaRepository<Approvals,Long> {

    Optional<Approvals> findByApplicationId(Long ApplicationId);
    List<Approvals> findByApplicationIdInAndStatus(List<Long> applicationIds, boolean status);

}
