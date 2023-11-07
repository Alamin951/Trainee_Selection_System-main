package com.example.JSS.service;

import com.example.JSS.entity.Applications;
import com.example.JSS.entity.Notice;

import java.util.List;

public interface NoticeService {
    void createNotice(Applications application, String message);
    List<Notice> getAllNotice();
    void createGlobalNotice(String message);
    List<Notice> getAllNotificationsWithApplicationId();
    List<Notice> getAllNotificationByApplicantId(Long applicantId);
}
