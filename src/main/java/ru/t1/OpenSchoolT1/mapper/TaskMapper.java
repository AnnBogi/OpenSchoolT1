package ru.t1.OpenSchoolT1.mapper;

import org.springframework.stereotype.Component;
import ru.t1.OpenSchoolT1.dto.TaskDTO;
import ru.t1.OpenSchoolT1.model.Task;

@Component
public class TaskMapper {

    public TaskDTO toDto(Task task) {
        if (task == null) {
            return null;
        }
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setUserId(task.getUserId());
        taskDTO.setStatus(task.getStatus());
        return taskDTO;
    }

    public Task toEntity(TaskDTO taskDTO) {
        if (taskDTO == null) {
            return null;
        }
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setUserId(taskDTO.getUserId());
        task.setStatus(taskDTO.getStatus());
        return task;
    }
}
