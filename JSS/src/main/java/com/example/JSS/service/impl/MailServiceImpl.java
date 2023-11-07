package com.example.JSS.service.impl;

import com.example.JSS.dto.MailDto;
import com.example.JSS.entity.DefaultMailMessage;
import com.example.JSS.entity.Mail;
import com.example.JSS.repository.DefaultMailMessageRepository;
import com.example.JSS.repository.MailRepository;
import com.example.JSS.service.MailService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final MailRepository mailRepository;
    private final DefaultMailMessageRepository defaultMailMessageRepository;
    private final ModelMapper modelMapper;
    private final JavaMailSender javaMailSender;

    @Override
    public Mail sendMail(MailDto mailDTO) {
        Mail mail = modelMapper.map(mailDTO, Mail.class);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mail.getRecipient());
            message.setSubject(mail.getSubject());
            message.setText(mail.getBody());
            javaMailSender.send(message);
        }catch (Exception e) {
            throw new MailSendException("Failed to send email");
        }

        return mailRepository.save(mail);
    }

    @Override
    public Mail sendCustomMail(Long messageId, String recipient) {
        DefaultMailMessage defaultMailMessage = defaultMailMessageRepository.findById(messageId)
                .orElseThrow(()-> new EntityNotFoundException("Invalid Mail Information!!!"));
        Mail mail= new Mail();
        mail.setRecipient(recipient);
        mail.setBody(defaultMailMessage.getBody());
        mail.setSubject(defaultMailMessage.getSubject());

        sendDefaultMail(mail);
        return mailRepository.save(mail);
    }

    @Override
    public Mail sendCustomMail(String messageName, String recipient) {
        DefaultMailMessage defaultMailMessage = defaultMailMessageRepository.findByName(messageName);
        if(defaultMailMessage == null){
            throw new EntityNotFoundException("Invalid Mail Information!!!");
        }
        Mail mail= new Mail();
        mail.setRecipient(recipient);
        mail.setBody(defaultMailMessage.getBody());
        mail.setSubject(defaultMailMessage.getSubject());

        sendDefaultMail(mail);
        return mailRepository.save(mail);
    }
    public void sendDefaultMail(Mail mail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mail.getRecipient());
            message.setSubject(mail.getSubject());
            message.setText(mail.getBody());
            javaMailSender.send(message);
        }catch (Exception e) {
            throw new MailSendException("Failed to send email");
        }
    }
}
