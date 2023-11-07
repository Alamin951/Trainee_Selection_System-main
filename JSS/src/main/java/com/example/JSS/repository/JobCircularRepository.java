package com.example.JSS.repository;

import com.example.JSS.entity.JobCircular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobCircularRepository extends JpaRepository<JobCircular, Long> {
}
