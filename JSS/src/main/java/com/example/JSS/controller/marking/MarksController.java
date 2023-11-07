package com.example.JSS.controller.marking;

import com.example.JSS.dto.marking.MarksDto;
import com.example.JSS.dto.marking.MarksResponseDto;
import com.example.JSS.entity.Marks;
import com.example.JSS.service.marking.MarksService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marks")
public class MarksController {
    private final MarksService marksService;

    @PostMapping
    public ResponseEntity<?> createMarks(@RequestBody MarksDto marksDto) {

        marksService.createMarks(marksDto);
        return new ResponseEntity<>("Marks added successfully!!!", HttpStatus.CREATED);

    }
    @GetMapping("/all")
    public ResponseEntity<List<MarksResponseDto>> getAllMarks() {
        List<MarksResponseDto> marksResponseDtoList = marksService.getAllMarks();
        return new ResponseEntity<>(marksResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<?> getMarksByApplication(@PathVariable Long applicationId) {
        try {
            List<Marks> marksList = marksService.getMarksByApplication(applicationId);
            return ResponseEntity.ok(marksList);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/application/{applicationId}/category/{categoryId}")
    public ResponseEntity<?> getMarksByApplicationIdAndCategory(@PathVariable Long applicationId,
                                                                @PathVariable Long categoryId) {
        try {
            Marks marksList = marksService.getMarksByApplicationIdAndCategory(applicationId, categoryId);
            return ResponseEntity.ok(marksList);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
