package com.example.JSS.controller.marking;

import com.example.JSS.dto.marking.WrittenMarksDto;
import com.example.JSS.entity.WrittenMarks;
import com.example.JSS.service.marking.WrittenMarksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/written_mark")
public class WrittenMarkingController {
    private final WrittenMarksService writtenMarksService;
    @PostMapping
    public ResponseEntity<?> uploadMark(@RequestBody WrittenMarksDto writtenMarksDto) throws IllegalAccessException {
            ResponseEntity<?> writtenMarks = writtenMarksService.createWrittenMark(writtenMarksDto);
            return new ResponseEntity<>(writtenMarks, HttpStatus.CREATED);

    }

}
