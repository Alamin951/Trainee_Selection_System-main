package com.example.JSS.repository;

import com.example.JSS.entity.Applicants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantsRepository extends JpaRepository<Applicants, Long> {
    Applicants findByEmail(String email);
}
