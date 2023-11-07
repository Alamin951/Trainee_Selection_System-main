package com.example.JSS.service.impl;

import com.example.JSS.entity.Applications;
import com.example.JSS.entity.Notice;
import com.example.JSS.repository.NoticeRepository;
import com.example.JSS.service.NoticeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    @Override
    public void createNotice(Applications application, String message) {
        Notice notice = new Notice();
        notice.setApplications(application);
        notice.setMessage(message);
        notice.setDate(LocalDateTime.now());
        noticeRepository.save(notice);
    }

    @Override
    public List<Notice> getAllNotice() {
        List<Notice> noticeList = noticeRepository.findAll();
        if (noticeList.isEmpty()){
            throw new EntityNotFoundException("NO_NOTICE_AVAILABLE!!!");
        }
        return noticeList;
    }

    @Override
    public void createGlobalNotice(String message) {
        Notice notice = new Notice();
        notice.setMessage(message);
        notice.setDate(LocalDateTime.now());
        noticeRepository.save(notice);
    }
    @Override
    public List<Notice> getAllNotificationsWithApplicationId() {
        return noticeRepository.findByApplicationsIsNull();
    }
    @Override
    public List<Notice> getAllNotificationByApplicantId(Long applicantId) {
        return noticeRepository.findByApplicationsApplicantId(applicantId);
    }
}
