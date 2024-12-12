package ru.t1.OpenSchoolT1.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.OpenSchoolT1.aspect.annotation.LogCreateTask;
import ru.t1.OpenSchoolT1.aspect.annotation.LogDeleteTask;
import ru.t1.OpenSchoolT1.aspect.annotation.LogUpdateTask;
import ru.t1.OpenSchoolT1.exception.TaskNotFoundException;
import ru.t1.OpenSchoolT1.model.Task;
import ru.t1.OpenSchoolT1.mapper.TaskMapper;
import ru.t1.OpenSchoolT1.dto.TaskDTO;
import ru.t1.OpenSchoolT1.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @LogCreateTask
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        Task createdTask = taskRepository.save(task);
        return taskMapper.toDto(createdTask);
    }

    public TaskDTO getTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return taskMapper.toDto(task);
    }

    @LogUpdateTask
    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        if (id == null || taskDTO == null) {
            throw new IllegalArgumentException("Task or ID cannot be null");
        }
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        Task task = taskMapper.toEntity(taskDTO);
        task.setId(id); // Убедитесь, что ID соответствует
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }

    @LogDeleteTask
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        throw new TaskNotFoundException(id);
    }

    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

}
