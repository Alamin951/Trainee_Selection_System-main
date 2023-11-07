package com.example.JSS.service.marking;

import com.example.JSS.dto.marking.WrittenMarksDto;
import com.example.JSS.entity.WrittenMarks;
import org.springframework.http.ResponseEntity;

public interface WrittenMarksService {
    ResponseEntity<?> createWrittenMark(WrittenMarksDto writtenMarksDto) throws IllegalAccessException;
    WrittenMarks updateWrittenMarks(String participantCode, double mark);
    
}
