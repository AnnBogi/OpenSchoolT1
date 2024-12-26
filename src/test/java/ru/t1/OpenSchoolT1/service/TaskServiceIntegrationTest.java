package ru.t1.OpenSchoolT1.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.OpenSchoolT1.dto.TaskDTO;
import ru.t1.OpenSchoolT1.enums.TaskStatus;
import ru.t1.OpenSchoolT1.exception.TaskNotFoundException;
import ru.t1.OpenSchoolT1.kafka.PostgresTestContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DirtiesContext
public class TaskServiceIntegrationTest extends PostgresTestContainer {

    @Autowired
    private TaskService taskService;

    @Test
    @Transactional
    public void testCreateTask() {
        TaskDTO taskDTO = new TaskDTO(null, "New Task", "Task Description", 1L, TaskStatus.CREATED);
        TaskDTO createdTask = taskService.createTask(taskDTO);

        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getTitle()).isEqualTo("New Task");
        assertThat(createdTask.getStatus()).isEqualTo(TaskStatus.CREATED);
    }

    @Test
    @Transactional
    public void testGetTask() {
        TaskDTO taskDTO = new TaskDTO(null, "New Task", "Task Description", 1L, TaskStatus.CREATED);
        TaskDTO createdTask = taskService.createTask(taskDTO);

        TaskDTO fetchedTask = taskService.getTask(createdTask.getId());

        assertThat(fetchedTask).isNotNull();
        assertThat(fetchedTask.getId()).isEqualTo(createdTask.getId());
        assertThat(fetchedTask.getTitle()).isEqualTo("New Task");
    }

    @Test
    @Transactional
    public void testUpdateTask() {
        TaskDTO taskDTO = new TaskDTO(null, "New Task", "Task Description", 1L, TaskStatus.CREATED);
        TaskDTO createdTask = taskService.createTask(taskDTO);

        TaskDTO updateDTO = new TaskDTO();
        updateDTO.setStatus(TaskStatus.UPDATED);
        updateDTO.setTitle("Updated Task");
        updateDTO.setDescription("Updated Description");
        updateDTO.setUserId(1L);

        TaskDTO updatedTask = taskService.updateTask(createdTask.getId(), updateDTO);

        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.getStatus()).isEqualTo(TaskStatus.UPDATED);
        assertThat(updatedTask.getTitle()).isEqualTo("Updated Task");
    }

    @Test
    @Transactional
    public void testDeleteTask() {
        TaskDTO taskDTO = new TaskDTO(null, "New Task", "Task Description", 1L, TaskStatus.CREATED);
        TaskDTO createdTask = taskService.createTask(taskDTO);

        boolean isDeleted = taskService.deleteTask(createdTask.getId());

        assertThat(isDeleted).isTrue();
        assertThatThrownBy(() -> taskService.getTask(createdTask.getId()))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task not found with id: " + createdTask.getId());
    }

    @Test
    @Transactional
    public void testGetAllTasks() {
        TaskDTO taskDTO1 = new TaskDTO(null, "Task 1", "Description 1", 1L, TaskStatus.CREATED);
        TaskDTO taskDTO2 = new TaskDTO(null, "Task 2", "Description 2", 2L, TaskStatus.CREATED);
        taskService.createTask(taskDTO1);
        taskService.createTask(taskDTO2);

        List<TaskDTO> tasks = taskService.getAllTasks();

        assertThat(tasks).hasSize(2);
    }
}
