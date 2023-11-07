package com.example.JSS.controller;

import com.example.JSS.dto.GlobalNoticeDto;
import com.example.JSS.entity.Notice;
import com.example.JSS.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping("/global")
    public ResponseEntity<String> createGlobalNotice(@RequestBody GlobalNoticeDto globalNoticeDto) {
        noticeService.createGlobalNotice(globalNoticeDto.getMessage());
        return ResponseEntity.ok("Notice Updated successfully!!!");
    }
    @GetMapping
    public ResponseEntity<List<Notice>> allNotice(){
        return ResponseEntity.ok(noticeService.getAllNotice());
    }

    @GetMapping("/global")
    public ResponseEntity<List<Notice>> getAllNotificationsWithApplicationId() {
        List<Notice> notices = noticeService.getAllNotificationsWithApplicationId();
        return ResponseEntity.ok(notices);
    }

    @GetMapping("/applicant/{userId}")
    public ResponseEntity<List<Notice>> getAllNotificationByApplicantId(@PathVariable("userId") Long applicantId) {
        List<Notice> notices = noticeService.getAllNotificationByApplicantId(applicantId);
        return ResponseEntity.ok(notices);
    }

}
