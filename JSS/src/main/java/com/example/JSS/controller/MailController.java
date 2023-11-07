package com.example.JSS.controller;

import com.example.JSS.dto.MailDto;
import com.example.JSS.entity.Mail;
import com.example.JSS.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;

    @PostMapping("/send")
    public Mail sendMail(@RequestBody MailDto mailDTO) {
        return mailService.sendMail(mailDTO);
    }
}
