package ru.t1.OpenSchoolT1.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import ru.t1.OpenSchoolT1.dto.TaskDTO;

@Slf4j
@RequiredArgsConstructor
public class KafkaTaskProducer {

    private final KafkaTemplate<String, TaskDTO> kafkaTemplate;

    @Value("${spring.kafka.topic.task-updates}")
    private String taskStatusTopic;

    public void sendTaskStatusUpdate(TaskDTO taskDTO) {
        kafkaTemplate.send(taskStatusTopic, taskDTO.getUserId().toString(), taskDTO);
    }
}
