package ru.t1.OpenSchoolT1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.t1.OpenSchoolT1.enums.TaskStatus;
import ru.t1.OpenSchoolT1.model.Task;


public class TaskDTO {

    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "User ID is mandatory")
    private Long userId;

    private TaskStatus status;

    public TaskDTO() {
    }

    public TaskDTO(Long id, String title, String description, Long userId, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    public TaskStatus getStatus() {
        return status;
    }

    public static TaskDTO fromEntity(Task task) {
        if (task == null) {
            return null;
        }
        return new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getUserId(), task.getStatus());
    }

    public Task toEntity() {
        Task task = new Task();
        task.setId(this.id);
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setUserId(this.userId);
        task.setStatus(this.status);
        return task;
    }
}
