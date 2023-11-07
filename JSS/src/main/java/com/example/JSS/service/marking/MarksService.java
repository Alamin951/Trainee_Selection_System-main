package com.example.JSS.service.marking;

import com.example.JSS.dto.marking.MarksDto;
import com.example.JSS.dto.marking.MarksResponseDto;
import com.example.JSS.entity.Marks;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MarksService {
    void createMarks(MarksDto marksDto);
    List<MarksResponseDto> getAllMarks();
    List<MarksResponseDto> getAllFinalCandidate();
    float getTotalMark(Long applicationId);
    Marks updateMarks(MarksDto marksDto);
    List<Marks> getMarksByApplication(Long applicationId);
    Marks getMarksByApplicationIdAndCategory(Long applicationId, Long categoryId);
}
