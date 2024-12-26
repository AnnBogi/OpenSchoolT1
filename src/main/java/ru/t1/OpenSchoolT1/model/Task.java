package ru.t1.OpenSchoolT1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.t1.OpenSchoolT1.enums.TaskStatus;


@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Description is mandatory")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;
    @NotNull(message = "User ID is mandatory")
    private Long userId;

    public Task() {

    }


    public Task(Long id,
                String title,
                String description,
                Long userId,
                TaskStatus status) {
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
}
