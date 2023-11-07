package com.example.JSS.service;

import com.example.JSS.dto.AdmitCardDto;
import com.example.JSS.dto.AdmitCardResponseDto;
import com.example.JSS.entity.AdmitCard;

import java.util.List;

public interface AdmitCardService {
    AdmitCard generateAdmitCard(Long applicationId);
    List<AdmitCardResponseDto> getAdmitCardByApplicant(Long applicantId);

/*
    List<AdmitCardDto> generateAdmitCards(List<Long> applicationIds);
*/

    /*AdmitCardDto generateAdmitCard(Long admitCardId);*/
    /*List<AdmitCardDto> getAllAdmitCards();
    AdmitCardDto getAdmitCardByApplicationId(Long applicationId);*/
}
