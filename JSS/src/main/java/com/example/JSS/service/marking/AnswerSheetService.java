package com.example.JSS.service.marking;

import com.example.JSS.dto.marking.AnswerSheetDto;
import com.example.JSS.dto.marking.AnswerSheetResponseDto;
import com.example.JSS.entity.AnswerSheet;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AnswerSheetService {
    ResponseEntity<?> addEvaluator(Long applicationId, Long evaluatorId);
    public List<AnswerSheetResponseDto> getAllApplicantCode(Long evaluatorId);
    public List<AnswerSheet> getAllWrittenCandidate();
    void createAnswerSheetForAllApplications();

    void createAnswerSheetByApplicationID(Long applicationId);

    String generateParticipantCode();
}
