package com.example.JSS.service.impl.marking;

import com.example.JSS.dto.marking.AnswerSheetResponseDto;
import com.example.JSS.entity.AnswerSheet;
import com.example.JSS.entity.Approvals;
import com.example.JSS.entity.Users;
import com.example.JSS.repository.ApprovalRepository;
import com.example.JSS.repository.UsersRepository;
import com.example.JSS.repository.marking.AnswerSheetRepository;
import com.example.JSS.service.marking.AnswerSheetService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AnswerSheetServiceImpl implements AnswerSheetService {
    private final AnswerSheetRepository answerSheetRepository;
    private final UsersRepository usersRepository;
    private final ApprovalRepository approvalRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> addEvaluator(Long applicationId, Long evaluatorId) {
        AnswerSheet answerSheet= answerSheetRepository.findByApplicationId(applicationId);
        if(answerSheet == null){
            throw new IllegalArgumentException("Application not Exist!!!");
        }

        Optional<Users> existingUsers = usersRepository.findById(evaluatorId);
        if (existingUsers.isEmpty() || !existingUsers.get().getRole().equals("evaluator")) {
            throw new IllegalArgumentException("Evaluator does not exist!");
        }

        answerSheet.setEvaluatorId(evaluatorId);
        answerSheetRepository.save(answerSheet);
        return null;
    }
    @Override
    public List<AnswerSheet> getAllWrittenCandidate(){
        List<AnswerSheet> answerSheetList = answerSheetRepository.findAll();
        if (answerSheetList.isEmpty()){
            throw new EntityNotFoundException("NO_WRITTEN_CANDIDATE_AVAILABLE");
        }
        return answerSheetList;
    }
    @Override
    public List<AnswerSheetResponseDto> getAllApplicantCode(Long evaluatorId){
        List<AnswerSheet> answerSheetList= answerSheetRepository.findByEvaluatorId(evaluatorId);
        if (answerSheetList.isEmpty()){
            throw new EntityNotFoundException("You have no Assigned Candidate");
        }

        return answerSheetList.stream()
                .map(answerSheet -> modelMapper.map(answerSheet, AnswerSheetResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void createAnswerSheetForAllApplications() {
        List<Approvals> approvals = approvalRepository.findAll();
        for (Approvals approval : approvals) {
            Long applicationId = approval.getApplicationId();
            boolean answerSheetExists = answerSheetRepository.existsByApplicationId(applicationId);
            if (answerSheetExists) {
                continue; // Skip creating a new answer sheet if it already exists
            }
            String participantCode = generateParticipantCode();
            AnswerSheet answerSheet = new AnswerSheet();
            answerSheet.setApplicationId(applicationId);
            answerSheet.setParticipantCode(participantCode);
            answerSheetRepository.save(answerSheet);
        }
    }

    public void createAnswerSheetByApplicationID(Long applicationId){
        try {
            boolean answerSheetExists = answerSheetRepository.existsByApplicationId(applicationId);
            if (answerSheetExists) {
                throw new IllegalArgumentException("ALREADY_AVAILABLE");
            }
            String participantCode = generateParticipantCode();
            AnswerSheet answerSheet = new AnswerSheet();
            answerSheet.setApplicationId(applicationId);
            answerSheet.setParticipantCode(participantCode);
            answerSheetRepository.save(answerSheet);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }


    @Override
    public String generateParticipantCode() {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(now);
        String randomString = generateRandomString();

        String participantCode = timestamp + randomString;

        // Generate a new participant code if it already exists in the database
        while (answerSheetRepository.findByParticipantCode(participantCode) != null) {
            randomString = generateRandomString();
            participantCode = timestamp + randomString;
        }

        return participantCode;
    }

    private String generateRandomString() {
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            sb.append(allowedChars.charAt(randomIndex));
        }
        return sb.toString();
    }
}
