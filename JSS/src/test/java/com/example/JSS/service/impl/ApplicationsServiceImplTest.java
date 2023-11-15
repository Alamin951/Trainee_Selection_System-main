package com.example.JSS.service.impl;

import com.example.JSS.entity.ApplicationResponseAdminDto;
import com.example.JSS.entity.Applications;
import com.example.JSS.repository.ApplicantsRepository;
import com.example.JSS.repository.ApplicationsRepository;
import com.example.JSS.repository.JobCircularRepository;
import com.example.JSS.repository.marking.MarksRepository;
import com.example.JSS.service.ApprovalService;
import com.example.JSS.service.NoticeService;
import com.example.JSS.service.StatusService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ApplicationsServiceImplTest {
    @Mock
    private ApplicationsRepository applicationsRepository;

    @Mock
    private JobCircularRepository jobCircularRepository;

    @Mock
    private ApplicantsRepository applicantsRepository;

    @Mock
    private StatusService statusService;

    @Mock
    private ApprovalService approvalService;

    @Mock
    private MarksRepository marksRepository;

    @Mock
    private NoticeService noticeService;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ApplicationsServiceImpl applicationsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    /*@Test
    public void testCreateApplication_ValidInput_ShouldCreateApplication() {
        // Mocking the input data
        Long circularId = 2L;
        Long applicantId = 4L;

        ApplicationsDto applicationsDto = new ApplicationsDto();
        applicationsDto.setCircularId(circularId);
        applicationsDto.setApplicantId(applicantId);

        // Mocking the JobCircular in the jobCircularRepository
        JobCircular jobCircular = new JobCircular();
        jobCircular.setCircularId(circularId);
        jobCircular.setCircularName("YSD_2_DevOps_2023");
        // Set other properties of the JobCircular

        // Mocking the existingApplications to be empty (applicant hasn't applied yet)
        Optional<Applications> existingApplications = Optional.empty();

        // Mocking the status
        Status status = new Status();
        status.setStatus("Applicant");

        // Mocking the saved Applications
        Applications savedApplications = new Applications();
        savedApplications.setApplicationId(100L);
        savedApplications.setJobCircular(jobCircular);
        savedApplications.setApplicantId(applicantId);
        // Set other properties of the saved Applications

        // Setting up the behavior of the mocks
        when(jobCircularRepository.findById(circularId)).thenReturn(Optional.of(jobCircular));
        when(applicationsRepository.findByJobCircularCircularIdAndApplicantId(circularId, applicantId)).thenReturn(existingApplications);
        when(statusService.getStatusByStatusName("Applicant")).thenReturn(status);
        when(applicationsRepository.save(any(Applications.class))).thenReturn(savedApplications);

        // Calling the method under test
        Applications createdApplications = applicationsService.createApplication(applicationsDto);

        // Assertions
        assertEquals(circularId, createdApplications.getJobCircular().getCircularId());
        assertEquals(applicantId, createdApplications.getApplicantId());
        // Add more assertions for other properties of the createdApplications if needed
    }*/

    /*@Test
    public void testUpdateApplications_SameStatus_ShouldNotUpdateStatus() {
        // Given
        Long applicationId = 1L;
        String status = "Applicant"; // Status is not changed

        Applications application = new Applications();
        // Set application properties with valid data
        application.setApplicationId(applicationId);
        Status previousStatus = new Status();
        // Set previousStatus properties with valid data
        previousStatus.setStatusId(1L);
        application.setStatus(previousStatus);

        when(applicationsRepository.findById(applicationId)).thenReturn(Optional.of(application));

        Status newStatus = new Status();
        // Set newStatus properties with valid data
        newStatus.setStatusId(1L); // Same status as previous status
        when(statusService.getStatusByStatusName(status)).thenReturn(newStatus);

        // When
        Applications updatedApplication = applicationsService.updateApplications(applicationId, status);

        // Then
        Assertions.assertNotNull(updatedApplication);
        Assertions.assertEquals(previousStatus, updatedApplication.getStatus());
        // Add more assertions based on expected behavior, such as verifying that no approval was created
    }*/

    /*@Test
    public void testUpdateApplications_NewStatusPendingApproval_ShouldCreateApproval() {
        // Given
        Long applicationId = 1L;
        String status = "Pending Approval";

        Applications application = new Applications();
        // Set application properties with valid data
        application.setApplicationId(applicationId);
        Status previousStatus = new Status();
        // Set previousStatus properties with valid data
        previousStatus.setStatusId(1L);
        application.setStatus(previousStatus);

        when(applicationsRepository.findById(applicationId)).thenReturn(Optional.of(application));

        Status newStatus = new Status();
        // Set newStatus properties with valid data
        newStatus.setStatusId(2L); // Pending Approval status
        when(statusService.getStatusByStatusName(status)).thenReturn(newStatus);

        // When
        Applications updatedApplication = applicationsService.updateApplications(applicationId, status);

        // Then
        Assertions.assertNotNull(updatedApplication);
        Assertions.assertEquals(newStatus, updatedApplication.getStatus());
        // Add more assertions based on expected behavior, such as verifying that an approval was created
        // and that the approval service's createApproval method was called with the correct parameters
    }*/

    /*@Test
    public void testUpdateApplications_NewStatusRejected_ShouldRemoveApproval() {
        // Given
        Long applicationId = 1L;
        String status = "Rejected";

        Applications application = new Applications();
        // Set application properties with valid data
        application.setApplicationId(applicationId);
        Status previousStatus = new Status();
        // Set previousStatus properties with valid data
        previousStatus.setStatusId(2L); // Pending Approval status
        application.setStatus(previousStatus);

        when(applicationsRepository.findById(applicationId)).thenReturn(Optional.of(application));

        Status newStatus = new Status();
        // Set newStatus properties with valid data
        newStatus.setStatusId(3L); // Rejected status
        when(statusService.getStatusByStatusName(status)).thenReturn(newStatus);

        // Mock the ApprovalService and its removeApproval method
        ApprovalService approvalService = mock(ApprovalService.class);

        // Create the ApplicationsServiceImpl with the mocked ApprovalService
        ApplicationsServiceImpl applicationsService = new ApplicationsServiceImpl(
                applicationsRepository,
                jobCircularRepository,
                applicantsRepository,
                statusService,
                modelMapper,
                approvalService, // Use the mocked ApprovalService
                marksRepository,
                noticeService
        );

        // When
        Applications updatedApplication = applicationsService.updateApplications(applicationId, status);

        // Then
        assertNotNull(updatedApplication);
        assertEquals(newStatus, updatedApplication.getStatus());

        // Verify that the removeApproval method of the mocked ApprovalService is called with the correct applicationId
        verify(approvalService, times(1)).removeApproval(applicationId);
        // Add more assertions based on expected behavior, if needed
    }*/


    @Test
    public void testUpdateApplications_InvalidStatus_ShouldThrowException() {
        // Given
        Long applicationId = 1L;
        String invalidStatus = "InvalidStatus";

        Applications application = new Applications();
        // Set application properties with valid data
        application.setApplicationId(applicationId);

        when(applicationsRepository.findById(applicationId)).thenReturn(Optional.of(application));

        when(statusService.getStatusByStatusName(invalidStatus)).thenReturn(null);

        // When/Then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> applicationsService.updateApplications(applicationId, invalidStatus));
    }

    @Test
    public void testDeleteApplication_ValidApplicationId_ShouldDeleteApplication() {
        // Given
        Long applicationId = 1L;

        // No need to mock the repository behavior, as we only want to verify that the delete method is called

        // When
        String result = applicationsService.deleteApplication(applicationId);

        // Then
        Assertions.assertEquals("Deleted successfully!!", result);
        // Add more assertions based on expected behavior, such as verifying that the delete method was called
    }


    @Test
    public void testGetApplicantById_InvalidApplicantId_ShouldThrowException() {
        // Given
        Long invalidApplicantId = -1L;

        when(applicationsRepository.findByApplicantId(invalidApplicantId)).thenReturn(Optional.empty());

        // When/Then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> applicationsService.getApplicantById(invalidApplicantId));
    }

    @Test
    public void testGetAllApplicationsDetails_ShouldReturnApplicationResponseAdminDtoList() {
        // Given
        List<Applications> applicationsList = new ArrayList<>();
        // Add Applications objects to the list with valid data

        when(applicationsRepository.findAll()).thenReturn(applicationsList);

        // When
        List<ApplicationResponseAdminDto> result = applicationsService.getAllApplicationsDetails();

        // Then
        Assertions.assertNotNull(result);
        // Add more assertions based on expected behavior, such as verifying that the ApplicationResponseAdminDto list is correctly populated
    }
}
