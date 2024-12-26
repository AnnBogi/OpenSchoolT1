package ru.t1.OpenSchoolT1.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import ru.t1.OpenSchoolT1.dto.TaskDTO;
import ru.t1.OpenSchoolT1.service.NotificationService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaTaskConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "task-updates", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void listenTaskStatusUpdate(List<TaskDTO> taskDTOs, Acknowledgment acknowledgment) {
        for (TaskDTO taskDTO : taskDTOs) {
            try {
                log.info("Received task update for task ID: {}", taskDTO.getId());
                notificationService.sendNotification(taskDTO);
                log.info("Successfully processed task update and sent notification for task ID: {}", taskDTO.getId());
            } catch (Exception e) {
                log.error("Error processing task update for task ID: {}", taskDTO.getId(), e);
            }
        }
        acknowledgment.acknowledge();
    }
}
