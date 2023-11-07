package com.example.JSS.service.impl.marking;

import com.example.JSS.dto.marking.ApplicationsResponseDto;
import com.example.JSS.dto.marking.MarksDto;
import com.example.JSS.dto.marking.MarksResponseDto;
import com.example.JSS.entity.Applications;
import com.example.JSS.entity.Marks;
import com.example.JSS.entity.MarksCategory;
import com.example.JSS.entity.Status;
import com.example.JSS.repository.ApplicationsRepository;
import com.example.JSS.repository.StatusRepository;
import com.example.JSS.repository.marking.MarksCategoryRepository;
import com.example.JSS.repository.marking.MarksRepository;
import com.example.JSS.service.ApplicationsService;
import com.example.JSS.service.marking.MarksService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarksServiceImpl implements MarksService {
    private final MarksRepository marksRepository;
    private final MarksCategoryRepository marksCategoryRepository;
    private final ApplicationsRepository applicationsRepository;
    private final ModelMapper modelMapper;

    private final ApplicationsService applicationsService;
    private final StatusRepository statusRepository;

    @Override
    @Transactional
    public void createMarks(MarksDto marksDto) {
        try{
            MarksCategory marksCategory= marksCategoryRepository.findById(marksDto.getMarksCategoryId()).orElseThrow(() -> new EntityNotFoundException("Marks Category is not available!!!"));

            Optional<Marks> existingMarks = marksRepository.findByApplicationsApplicationIdAndMarksCategoryCategoryId(marksDto.getApplicationId(), marksDto
                    .getMarksCategoryId());
            if(existingMarks.isPresent()){
                throw new IllegalArgumentException("Already Marked!!!");
            }
            Applications applications= applicationsRepository.findById(marksDto.getApplicationId())
                    .orElseThrow(()-> new EntityNotFoundException("Invalid Application!!!"));
            Marks marks = modelMapper.map(marksDto, Marks.class);
            marks.setMarksCategory(marksCategory);
            marks.setApplications(applications);
            marks.setDate(new Date());
            marksRepository.save(marks);
            Status status = applications.getStatus();
            Status currentStatus= statusRepository.findByStatus(status.getStatus())
                    .orElseThrow(()-> new EntityNotFoundException("Status Not available!!!"));
            Long newStatusId = currentStatus.getStatusId() + 1;
            Status newStatus = statusRepository.findById(newStatusId)
                    .orElseThrow(()-> new EntityNotFoundException("Status Not available!!!"));
            if (marksDto.getMarks()>= marksCategory.getMinMark()){
                applicationsService.updateApplications(applications.getApplicationId(), newStatus.getStatus());
            }

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MarksResponseDto> getAllMarks() {
        List<Marks> marksList = marksRepository.findAll();

        if (marksList.isEmpty()) {
            throw new EntityNotFoundException("No Marks found.");
        }

        return marksList.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MarksResponseDto> getAllFinalCandidate() {

        return null;
    }

    @Override
    public float getTotalMark(Long applicationId) {
        float totalMarks = 0.0f;
        List<Marks> marksList = marksRepository.findByApplicationsApplicationId(applicationId)
                .orElseThrow(()->  new EntityNotFoundException("NOT_FOUND"));
        for (Marks marks : marksList) {
            totalMarks += marks.getMarks();
        }
        return totalMarks;
    }

    @Override
    public Marks updateMarks(MarksDto marksDto) {
        return null;
    }

    @Override
    public List<Marks> getMarksByApplication(Long applicationId) {
        return marksRepository.findByApplicationsApplicationId(applicationId)
                .orElseThrow(()-> new EntityNotFoundException("Application Mark not available!!!"));
    }

    @Override
    public Marks getMarksByApplicationIdAndCategory(Long applicationId, Long categoryId) {
        return marksRepository.findByApplicationsApplicationIdAndMarksCategoryCategoryId(applicationId, categoryId)
                .orElseThrow(()-> new EntityNotFoundException("Application Mark not available!!!"));
    }

    private MarksResponseDto convertToResponseDto(Marks marks) {
        MarksResponseDto responseDto = modelMapper.map(marks, MarksResponseDto.class);
        responseDto.setApplications(modelMapper.map(marks.getApplications(), ApplicationsResponseDto.class));
        responseDto.setMarksCategory(modelMapper.map(marks.getMarksCategory(), MarksCategory.class));
        return responseDto;
    }

}
