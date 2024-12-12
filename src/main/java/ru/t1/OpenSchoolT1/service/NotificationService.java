package ru.t1.OpenSchoolT1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.t1.OpenSchoolT1.dto.TaskDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String email;

    public void sendNotification(TaskDTO taskDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(email);
        message.setSubject("Task Status Update");
        message.setText("Task with ID: " + taskDTO.getUserId() + " has been updated. New status: " + taskDTO.getStatus());

        try {
            emailSender.send(message);
            log.info("Email sent successfully for task ID: {}", taskDTO.getUserId());
        } catch (Exception e) {
            log.error("Failed to send email for task ID: {}. Error: {}", taskDTO.getUserId(), e.getMessage(), e);
        }
    }
}