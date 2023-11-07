package com.example.JSS.service.impl.marking;

import com.example.JSS.dto.marking.WrittenMarksDto;
import com.example.JSS.entity.AnswerSheet;
import com.example.JSS.entity.MarksCategory;
import com.example.JSS.entity.WrittenMarks;
import com.example.JSS.repository.marking.AnswerSheetRepository;
import com.example.JSS.repository.marking.MarksCategoryRepository;
import com.example.JSS.repository.marking.WrittenMarksRepository;
import com.example.JSS.service.ApplicationsService;
import com.example.JSS.service.marking.WrittenMarksService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WrittenMarksServiceImpl implements WrittenMarksService {
    private final WrittenMarksRepository writtenMarksRepository;
    private final MarksCategoryRepository marksCategoryRepository;
    private final AnswerSheetRepository answerSheetRepository;
    private final ModelMapper modelMapper;

    private final ApplicationsService applicationsService;

    @Override
    @Transactional
    public ResponseEntity<?> createWrittenMark(WrittenMarksDto writtenMarksDto) {
        try {

            MarksCategory marksCategory = marksCategoryRepository.findByCategoryName("Written")
                    .orElseThrow(() -> new IllegalArgumentException("Marks Category is not available!!!"));

            Optional<WrittenMarks> existingWrittenMark = writtenMarksRepository.findByParticipantCode(writtenMarksDto.getParticipantCode());
            if (existingWrittenMark.isPresent()) {
                throw new IllegalArgumentException("Already marked!!!");
            }

            AnswerSheet answerSheet = answerSheetRepository.findByParticipantCode(writtenMarksDto.getParticipantCode());
            if (!answerSheet.getEvaluatorId().equals(writtenMarksDto.getEvaluatorId())) {
                throw new IllegalAccessException("You are not authorized to upload this mark!!!");
            }

            WrittenMarks writtenMarks = modelMapper.map(writtenMarksDto, WrittenMarks.class);
            writtenMarks.setMarksCategory(marksCategory);

            WrittenMarks createdWrittenMark = writtenMarksRepository.save(writtenMarks);

            if (writtenMarksDto.getMark()>marksCategory.getMinMark()){
                applicationsService.updateApplications(answerSheet.getApplicationId(), "Aptitude Test");
            }

            return new ResponseEntity<>(createdWrittenMark, HttpStatus.CREATED);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>("Unauthorized access: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public WrittenMarks updateWrittenMarks(String participantCode, double mark) {
        return null;
    }
}
