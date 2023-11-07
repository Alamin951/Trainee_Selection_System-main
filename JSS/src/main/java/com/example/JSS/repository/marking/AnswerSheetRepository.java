package com.example.JSS.repository.marking;

import com.example.JSS.entity.AnswerSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerSheetRepository extends JpaRepository<AnswerSheet,Long> {
    AnswerSheet findByParticipantCode(String participantCode);
    AnswerSheet findByApplicationId(Long applicationId);
    List<AnswerSheet> findByEvaluatorId(Long evaluatorId);
    boolean existsByApplicationId(Long applicationId);
}
