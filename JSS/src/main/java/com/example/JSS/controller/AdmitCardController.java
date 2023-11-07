package com.example.JSS.controller;

import com.example.JSS.dto.AdmitCardDto;
import com.example.JSS.dto.AdmitCardResponseDto;
import com.example.JSS.entity.AdmitCard;
import com.example.JSS.service.AdmitCardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admit-card")
public class AdmitCardController {
    private final AdmitCardService admitCardService;
    @PostMapping("/{applicationId}")
    public ResponseEntity<AdmitCard> generateAdmitCard(@PathVariable("applicationId") Long applicationId) {
        AdmitCard admitCard = admitCardService.generateAdmitCard(applicationId);
        return ResponseEntity.ok(admitCard);
    }
    @GetMapping("/{applicantId}")
    public ResponseEntity<List<AdmitCardResponseDto>> getAdmitCard(@PathVariable("applicantId") Long applicantId) {
        List<AdmitCardResponseDto> admitCard = admitCardService.getAdmitCardByApplicant(applicantId);
        return ResponseEntity.ok(admitCard);
    }

    /*@PostMapping
    ResponseEntity<?> generateAdmitCard(@RequestBody List<Long> applicationIds){
            List<AdmitCardDto> admitCards = admitCardService.generateAdmitCards(applicationIds);
            return ResponseEntity.ok(admitCards);
    }*/

//    @GetMapping("/{applicationId}")
//    public ResponseEntity<?> getAdmitCardByApplicationId(@PathVariable Long applicationId) {
//        AdmitCardDto admitCardDto = admitCardService.getAdmitCardByApplicationId(applicationId);
//            return ResponseEntity.ok(admitCardDto);
//    }
}
