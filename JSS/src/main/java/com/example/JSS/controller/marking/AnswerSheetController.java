package com.example.JSS.controller.marking;


import com.example.JSS.dto.marking.AnswerSheetDto;
import com.example.JSS.dto.marking.AnswerSheetResponseDto;
import com.example.JSS.entity.AnswerSheet;
import com.example.JSS.service.marking.AnswerSheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answerSheet")
public class AnswerSheetController {
    private final AnswerSheetService answerSheetService;

    @PatchMapping
    public ResponseEntity<String> createAnswerSheet(@RequestBody AnswerSheetDto answerSheetDto) {
        ResponseEntity<?> createdAnswerSheetDto = answerSheetService.addEvaluator(answerSheetDto.getApplicationId(), answerSheetDto.getEvaluatorId());
        return ResponseEntity.ok("Updated");
    }
    @GetMapping
    public ResponseEntity<List<AnswerSheet>> getAllCandidate(){
        List<AnswerSheet> answerSheets= answerSheetService.getAllWrittenCandidate();
        return ResponseEntity.ok(answerSheets);
    }
    @GetMapping("/{evaluatorId}")
    public ResponseEntity<List<AnswerSheetResponseDto>> getAllCandidateCode(@PathVariable("evaluatorId") Long evaluatorId){
        List<AnswerSheetResponseDto> answerSheetResponseDtoList= answerSheetService.getAllApplicantCode(evaluatorId);
        return ResponseEntity.ok(answerSheetResponseDtoList);
    }


    @PostMapping("/create-for-all")
    public ResponseEntity<String> createAnswerSheetsForAllApplications() {
        answerSheetService.createAnswerSheetForAllApplications();
        return ResponseEntity.ok("Answer sheets created for all applications.");
    }

}
