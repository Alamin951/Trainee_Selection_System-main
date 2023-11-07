package com.example.JSS.service.impl;

import com.example.JSS.dto.AdmitCardDto;
import com.example.JSS.dto.AdmitCardResponseDto;
import com.example.JSS.entity.AdmitCard;
import com.example.JSS.entity.Applicants;
import com.example.JSS.entity.Applications;
import com.example.JSS.entity.Approvals;
import com.example.JSS.repository.AdmitCardRepository;
import com.example.JSS.repository.ApplicantsRepository;
import com.example.JSS.repository.ApplicationsRepository;
import com.example.JSS.repository.ApprovalRepository;
import com.example.JSS.service.AdmitCardService;
import com.example.JSS.service.ApplicationsService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AdmitCardServiceImpl implements AdmitCardService {
    private final AdmitCardRepository admitCardRepository;
    private final ApplicationsRepository applicationsRepository;
    private final ApplicantsRepository applicantsRepository;
    private final ModelMapper modelMapper;


    @Override
    public AdmitCard generateAdmitCard(Long applicationId) {

        Applications application = applicationsRepository.findById(applicationId)
                .orElseThrow(()->new EntityNotFoundException("No_APPLICATION_AVAILABLE!!!"));// Get application by applicationId from the database

        // Calculate the expiration date (30 days from the current date)
        LocalDateTime expiringDate = LocalDateTime.now().plusDays(30);

        Applicants applicant = applicantsRepository.findById(application.getApplicantId())
                .orElseThrow(()->new EntityNotFoundException("No_APPLICANT_AVAILABLE!!!"));

        // Generate the QR code data with the required information
        String qrCodeData = String.format("Applicant Name: %s\nCircular Name: %s\nGmail: %s\nContact Number: %s\nApplication ID: %d",
                applicant.getFirstName()+" "+applicant.getLastName(),
                application.getJobCircular().getCircularName(),
                applicant.getEmail(),
                applicant.getContactNumber(),
                application.getApplicationId());
        // Generate the QR code image
        String qrCode = generateQRCodeImage(qrCodeData);
        // Save the admit card details to the database
        AdmitCard admitCard = new AdmitCard();
        admitCard.setApplications(application);
        admitCard.setQrCode(qrCode);
        admitCard.setExpiringDate(expiringDate);
        return admitCardRepository.save(admitCard);
    }

    private String generateQRCodeImage(String data) {
        int width = 300;
        int height = 300;
        String format = "png";
        ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;

        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, width, height,
                    getQRCodeHints(errorCorrectionLevel));

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);

            byte[] qrCodeBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(qrCodeBytes);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<AdmitCardResponseDto> getAdmitCardByApplicant(Long applicantId) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<AdmitCard> admitCards = admitCardRepository.findByApplicationsApplicantIdAndExpiringDateAfter(applicantId, currentDateTime);

        // Map the list of AdmitCard entities to AdmitCardResponseDto list using ModelMapper
        List<AdmitCardResponseDto> dtoList = new ArrayList<>();
        for (AdmitCard admitCard : admitCards) {
            AdmitCardResponseDto dto = modelMapper.map(admitCard, AdmitCardResponseDto.class);
            Applicants applicant = applicantsRepository.findById(applicantId)
                            .orElseThrow(()->new EntityNotFoundException("INVALID_APPLICANT!!!"));
            dto.setApplicants(applicant);
            dtoList.add(dto);
        }

        return dtoList;
    }

    private static Map<EncodeHintType, Object> getQRCodeHints(ErrorCorrectionLevel errorCorrectionLevel) {
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
        return hints;
    }
    /*@Override
    public List<AdmitCardDto> generateAdmitCards(List<Long> applicationIds) {
        List<AdmitCardDto> admitCardDtos= new ArrayList<>();
        for (Long applicationId: applicationIds){
            AdmitCardDto admitCardDto = generateAdmitCard(applicationId);
            admitCardDtos.add(admitCardDto);
        }
        return admitCardDtos;
    }*/

    /*@Override
    public AdmitCardDto generateAdmitCard(Long applicationId) {
        Approvals approvals= approvalRepository.findByApplicationId(applicationId)
                .orElseThrow(()-> new EntityNotFoundException("Approvals not found for applicationId: " + applicationId));
        Long applicantId = applicationsService.getApplicantId(applicationId);

        Applicants applicants = applicantsRepository.findById(applicantId)
                .orElseThrow(()-> new EntityNotFoundException("Invalid Applicant!!!"));
        String applicantName = applicants.getFirstName()+" "+applicants.getLastName();
        AdmitCard admitCard = new AdmitCard();
        *//*admitCard.setApprovals(approvals);
        admitCard.setApplicants(applicants);*//*
        admitCard.setQrCode(generateQrCode(applicantName, applicants.getEmail(), applicants.getContactNumber(), applicationId));

        AdmitCard savedAdmitCard = admitCardRepository.save(admitCard);

        return modelMapper.map(savedAdmitCard,AdmitCardDto.class);
    }*/

    /*@Override
    public List<AdmitCardDto> getAllAdmitCards() {
        return null;
    }

    @Override
    public AdmitCardDto getAdmitCardByApplicationId(Long applicationId) {
        AdmitCard admitCard = admitCardRepository.findByApprovalsApplicationId(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Admit Card not found for applicationId: " + applicationId));
        return modelMapper.map(admitCard, AdmitCardDto.class);
    }

    private String generateQrCode(String name, String email, String phoneNumber, Long applicationId) {
        try {
            // QR code data
            String qrCodeData = name + "\n" + email + "\n" + phoneNumber;

            // QR code size (pixels)
            int size = 200;

            // Configure QR code hints
            Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            // Create QR code writer
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            // Create QR code bit matrix
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, size, size, hints);

            // Convert bit matrix to buffered image
            BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Create file path to save the QR code image
            String fileName = applicationId + "-" + name + ".png";
            String filePath = "src/main/resources/admit-cards/" + fileName;

            // Save QR code image to file
            File outputFile = new File(filePath);
            ImageIO.write(qrCodeImage, "png", outputFile);
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/
}
