package com.example.JSS.service.impl;

import com.example.JSS.dto.ApplicantsDto;
import com.example.JSS.dto.PendingApprovalDto;
import com.example.JSS.dto.UsersDto;
import com.example.JSS.entity.Applicants;
import com.example.JSS.entity.Applications;
import com.example.JSS.entity.Users;
import com.example.JSS.exception.DuplicateEmailException;
import com.example.JSS.model.RegisterRequest;
import com.example.JSS.repository.ApplicantsRepository;
import com.example.JSS.repository.UsersRepository;
import com.example.JSS.service.ApplicantsService;
import com.example.JSS.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicantsServiceImpl implements ApplicantsService {
    private final ApplicantsRepository applicantsRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public List<ApplicantsDto> getAllApplicants() {
        List<Applicants> applicantsList = applicantsRepository.findAll();
        return applicantsList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ApplicantsDto> getApplicantById(Long applicantId) {
        Optional<Applicants> optionalApplicant = applicantsRepository.findById(applicantId);
        if (optionalApplicant.isEmpty()){
            throw new EntityNotFoundException("INVALID_APPLICANT");
        }
        return optionalApplicant.map(this::toDto);
    }

    @Override
    @Transactional
    public ApplicantsDto createApplicant(ApplicantsDto applicantsDto) {
        Applicants existingApplicant= applicantsRepository.findByEmail(applicantsDto.getEmail());
        if(existingApplicant!=null){
            throw new IllegalArgumentException("ALREADY_EXIST!!!");
        }
        Applicants applicant = modelMapper.map(applicantsDto, Applicants.class);
        Applicants createdApplicant = applicantsRepository.save(applicant);

        UsersDto userDto = modelMapper.map(applicantsDto, UsersDto.class);
        userDto.setRole("applicant");
        RegisterRequest registerRequest= modelMapper.map(userDto, RegisterRequest.class);
        userService.registerUser(registerRequest);
        return modelMapper.map(createdApplicant, ApplicantsDto.class);
    }

    @Override
    public ApplicantsDto updateApplicant(Long applicantId, ApplicantsDto applicantsDto) {
        Optional<Applicants> optionalApplicant = applicantsRepository.findById(applicantId);
        if (optionalApplicant.isPresent()) {
            Applicants applicant = optionalApplicant.get();
            modelMapper.map(applicantsDto, applicant);
            Applicants updatedApplicant = applicantsRepository.save(applicant);
            return modelMapper.map(updatedApplicant, ApplicantsDto.class);
        }
        throw new IllegalArgumentException("Applicant with ID " + applicantId + " not found");
    }




    private ApplicantsDto toDto(Applicants applicant) {
        return modelMapper.map(applicant, ApplicantsDto.class);
    }

    private UsersDto toDto(Users user) {
        return modelMapper.map(user, UsersDto.class);
    }
}
