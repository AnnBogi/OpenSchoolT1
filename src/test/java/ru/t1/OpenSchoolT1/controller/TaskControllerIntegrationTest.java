package ru.t1.OpenSchoolT1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.t1.OpenSchoolT1.dto.TaskDTO;
import ru.t1.OpenSchoolT1.enums.TaskStatus;
import ru.t1.OpenSchoolT1.repository.TaskRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        taskRepository.deleteAll();
    }

    @Test
    public void testCreateTask() throws Exception {
        TaskDTO taskDTO = new TaskDTO(null, "Test Title", "Test Description", 1L, TaskStatus.CREATED);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    public void testGetTask_Success() throws Exception {
        TaskDTO taskDTO = new TaskDTO(null, "Test Title", "Test Description", 1L, TaskStatus.CREATED);
        TaskDTO createdTask = taskRepository.save(taskDTO.toEntity()).toDTO();

        mockMvc.perform(get("/api/tasks/{id}", createdTask.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    public void testGetTask_NotFound() throws Exception {
        mockMvc.perform(get("/api/tasks/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateTask_Success() throws Exception {
        TaskDTO taskDTO = new TaskDTO(null, "Test Title", "Test Description", 1L, TaskStatus.CREATED);
        TaskDTO createdTask = taskRepository.save(taskDTO.toEntity()).toDTO();

        TaskDTO updatedTaskDTO = new TaskDTO(createdTask.getId(), "Updated Title", "Updated Description", 1L, TaskStatus.UPDATED);

        mockMvc.perform(put("/api/tasks/{id}", createdTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTaskDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    public void testDeleteTask_Success() throws Exception {
        TaskDTO taskDTO = new TaskDTO(null, "Test Title", "Test Description", 1L, TaskStatus.CREATED);
        TaskDTO createdTask = taskRepository.save(taskDTO.toEntity()).toDTO();

        mockMvc.perform(delete("/api/tasks/{id}", createdTask.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/tasks/{id}", createdTask.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTask_NotFound() throws Exception {
        mockMvc.perform(delete("/api/tasks/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllTasks() throws Exception {
        TaskDTO taskDTO1 = new TaskDTO(null, "Task 1", "Description 1", 1L, TaskStatus.CREATED);
        TaskDTO taskDTO2 = new TaskDTO(null, "Task 2", "Description 2", 2L, TaskStatus.UPDATED);
        taskRepository.save(taskDTO1.toEntity());
        taskRepository.save(taskDTO2.toEntity());

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].title").value("Task 2"));
    }
}
