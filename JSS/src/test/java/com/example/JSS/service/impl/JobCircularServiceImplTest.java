package com.example.JSS.service.impl;

import com.example.JSS.dto.JobCircularDto;
import com.example.JSS.entity.JobCircular;
import com.example.JSS.repository.JobCircularRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import java.util.Date;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class JobCircularServiceImplTest {
    @Mock
    private JobCircularRepository jobCircularRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private JobCircularServiceImpl jobCircularService;


    @BeforeEach
    void setUp() {
        jobCircularRepository = mock(JobCircularRepository.class);
        modelMapper = new ModelMapper();
        jobCircularService = new JobCircularServiceImpl(jobCircularRepository, modelMapper);
    }

    @Test
    void testCreateJobCircular() {
        // Input DTO
        JobCircularDto inputDto = new JobCircularDto();
        inputDto.setCircularName("Test Job Circular");
        inputDto.setDescription("This is a test circular.");
        inputDto.setOpenDate(new Date()); // Set the open date
        inputDto.setApplicationDeadline(new Date()); // Set the application deadline

        // Expected Entity
        JobCircular expectedEntity = new JobCircular();
        expectedEntity.setCircularId(50L); // Assuming circularId is generated as 50
        expectedEntity.setCircularName(inputDto.getCircularName());
        expectedEntity.setDescription(inputDto.getDescription());
        expectedEntity.setOpenDate(inputDto.getOpenDate());
        expectedEntity.setApplicationDeadline(inputDto.getApplicationDeadline());

        // Mock the ModelMapper and configure it to return the expected entity
        ModelMapper modelMapper = mock(ModelMapper.class);
        when(modelMapper.map(inputDto, JobCircular.class)).thenReturn(expectedEntity);

        // Create the JobCircularServiceImpl with the mocked ModelMapper
        JobCircularServiceImpl jobCircularService = new JobCircularServiceImpl(jobCircularRepository, modelMapper);

        when(jobCircularRepository.save(any(JobCircular.class))).thenReturn(expectedEntity);

        // Create Job Circular
        JobCircular createdJobCircular = jobCircularService.createJobCircular(inputDto);

        // Assertions
        assertNotNull(createdJobCircular);
        assertEquals(expectedEntity.getCircularId(), createdJobCircular.getCircularId());
        assertEquals(expectedEntity.getCircularName(), createdJobCircular.getCircularName());
        assertEquals(expectedEntity.getDescription(), createdJobCircular.getDescription());
        assertEquals(expectedEntity.getOpenDate(), createdJobCircular.getOpenDate());
        assertEquals(expectedEntity.getApplicationDeadline(), createdJobCircular.getApplicationDeadline());

        // Verify that the save method is called with the correct entity
        verify(jobCircularRepository, times(1)).save(expectedEntity);
    }


    @Test
    void testUpdateJobCircular_ValidCircularId() {
        // Existing Entity in the repository
        JobCircular existingEntity = new JobCircular();
        existingEntity.setCircularId(1L);
        existingEntity.setCircularName("Existing Circular");
        existingEntity.setDescription("This is an existing circular.");
        existingEntity.setOpenDate(new Date());
        existingEntity.setApplicationDeadline(new Date());

        // Input DTO for the update
        JobCircularDto inputDto = new JobCircularDto();
        inputDto.setCircularName("Updated Circular");
        inputDto.setDescription("This is an updated circular.");
        inputDto.setOpenDate(new Date()); // Set the open date for the update
        inputDto.setApplicationDeadline(new Date()); // Set the application deadline for the update

        // Expected Entity after update
        JobCircular expectedEntity = new JobCircular();
        expectedEntity.setCircularId(1L);
        expectedEntity.setCircularName(inputDto.getCircularName());
        expectedEntity.setDescription(inputDto.getDescription());
        expectedEntity.setOpenDate(inputDto.getOpenDate());
        expectedEntity.setApplicationDeadline(inputDto.getApplicationDeadline());

        // Mock the ModelMapper and configure it to return the expected entity
        ModelMapper modelMapper = mock(ModelMapper.class);
        when(modelMapper.map(inputDto, JobCircular.class)).thenReturn(expectedEntity);

        // Mock the JobCircularRepository and configure it to return the existingEntity when findById is called
        JobCircularRepository jobCircularRepository = mock(JobCircularRepository.class);
        when(jobCircularRepository.findById(existingEntity.getCircularId())).thenReturn(Optional.of(existingEntity));
        when(jobCircularRepository.save(any(JobCircular.class))).thenReturn(expectedEntity);

        // Create the JobCircularServiceImpl with the mocked ModelMapper and JobCircularRepository
        JobCircularServiceImpl jobCircularService = new JobCircularServiceImpl(jobCircularRepository, modelMapper);

        // Update Job Circular
        JobCircular updatedJobCircular = jobCircularService.updateJobCircular(existingEntity.getCircularId(), inputDto);

        // Assertions
        assertNotNull(updatedJobCircular);
        assertEquals(expectedEntity.getCircularId(), updatedJobCircular.getCircularId());
        assertEquals(expectedEntity.getCircularName(), updatedJobCircular.getCircularName());
        assertEquals(expectedEntity.getDescription(), updatedJobCircular.getDescription());
        assertEquals(expectedEntity.getOpenDate(), updatedJobCircular.getOpenDate());
        assertEquals(expectedEntity.getApplicationDeadline(), updatedJobCircular.getApplicationDeadline());

        // Verify that the findById and save methods are called with the correct parameters
        verify(jobCircularRepository, times(1)).findById(existingEntity.getCircularId());
        verify(jobCircularRepository, times(1)).save(expectedEntity);
    }


    @Test
    void testUpdateJobCircular_InvalidCircularId() {
        // Input DTO for the update
        JobCircularDto inputDto = new JobCircularDto();
        inputDto.setCircularName("Updated Circular");
        inputDto.setDescription("This is an updated circular.");
        inputDto.setOpenDate(new Date()); // Set the open date for the update
        inputDto.setApplicationDeadline(new Date()); // Set the application deadline for the update

        // Circular with invalid ID (not found in repository)
        Long invalidCircularId = 999L;

        when(jobCircularRepository.findById(invalidCircularId)).thenReturn(Optional.empty());

        // Update Job Circular with invalid ID should throw an IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> jobCircularService.updateJobCircular(invalidCircularId, inputDto));

        // Verify that the findById method is called with the invalidCircularId
        verify(jobCircularRepository, times(1)).findById(invalidCircularId);
    }
}
