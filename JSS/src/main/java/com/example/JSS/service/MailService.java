package com.example.JSS.service;

import com.example.JSS.dto.MailDto;
import com.example.JSS.entity.Mail;

public interface MailService {
    Mail sendMail(MailDto mailDTO);
    Mail sendCustomMail(Long messageId, String recipient);
    Mail sendCustomMail(String messageName, String recipient);


}
