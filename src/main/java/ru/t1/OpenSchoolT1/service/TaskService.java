package ru.t1.OpenSchoolT1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.OpenSchoolT1.aspect.annotation.LogCreateTask;
import ru.t1.OpenSchoolT1.aspect.annotation.LogDeleteTask;
import ru.t1.OpenSchoolT1.aspect.annotation.LogUpdateTask;
import ru.t1.OpenSchoolT1.model.Task;
import ru.t1.OpenSchoolT1.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @LogCreateTask
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> getTask(Long id) {
        return taskRepository.findById(id);
    }

    @LogUpdateTask
    public Task updateTask(Long id, Task task) {
        if (id == null || task == null) {
            throw new IllegalArgumentException("Task or ID cannot be null");
        }
        return taskRepository.save(task);
    }

    @LogDeleteTask
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
