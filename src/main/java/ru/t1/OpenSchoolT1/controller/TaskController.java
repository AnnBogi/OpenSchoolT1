package ru.t1.OpenSchoolT1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.t1.OpenSchoolT1.model.Task;
import ru.t1.OpenSchoolT1.dto.TaskDTO;
import ru.t1.OpenSchoolT1.mapper.TaskMapper;
import ru.t1.OpenSchoolT1.service.TaskService;

import java.util.List;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        Task task = taskDTO.toEntity();
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(TaskDTO.fromEntity(createdTask));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long id) {
        TaskDTO task = taskService.getTask(id);
        return ResponseEntity.ok(TaskDTO.fromEntity(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) {
        Task task = taskDTO.toEntity();
        Task updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.ok(TaskDTO.fromEntity(updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.deleteTask(id));
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        List<TaskDTO> taskDTOs = tasks.stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDTOs);
    }
}
