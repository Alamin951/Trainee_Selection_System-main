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
    private final UsersRepository usersRepository;
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
        return optionalApplicant.map(this::toDto);
    }

    @Override
    public ApplicantsDto createApplicant(ApplicantsDto applicantsDto) {
        Applicants existingApplicant= applicantsRepository.findByEmail(applicantsDto.getEmail());
        //Custom exception.
        Applicants applicant = modelMapper.map(applicantsDto, Applicants.class);
        Applicants createdApplicant = applicantsRepository.save(applicant);

        // Create the user
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

    @Override
    public void deleteApplicant(Long applicantId) {
        applicantsRepository.deleteById(applicantId);
    }


    private ApplicantsDto toDto(Applicants applicant) {
        return modelMapper.map(applicant, ApplicantsDto.class);
    }

    private UsersDto toDto(Users user) {
        return modelMapper.map(user, UsersDto.class);
    }
}
