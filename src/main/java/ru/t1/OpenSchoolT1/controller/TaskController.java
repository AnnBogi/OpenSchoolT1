package ru.t1.OpenSchoolT1.controller;

import ru.t1.OpenSchoolT1.service.TaskService;
import ru.t1.OpenSchoolT1.dto.TaskDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskDTO createTask(@Valid @RequestBody TaskDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }

    @GetMapping("/{id}")
    public TaskDTO getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) {
        return taskService.updateTask(id, taskDTO);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }
}
