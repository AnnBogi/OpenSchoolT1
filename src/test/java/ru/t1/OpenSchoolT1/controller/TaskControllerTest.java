package ru.t1.OpenSchoolT1.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.t1.OpenSchoolT1.controller.TaskController;
import ru.t1.OpenSchoolT1.dto.TaskDTO;
import ru.t1.OpenSchoolT1.service.TaskService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTask() {
        TaskDTO taskDTO = new TaskDTO(1L, "Test Task", "Description", 1L, null);
        when(taskService.createTask(any(TaskDTO.class))).thenReturn(taskDTO);

        TaskDTO createdTask = taskController.createTask(taskDTO);

        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getTitle());
        verify(taskService, times(1)).createTask(taskDTO);
    }

    @Test
    public void testGetTask() {
        TaskDTO taskDTO = new TaskDTO(1L, "Test Task", "Description", 1L, null);
        when(taskService.getTask(1L)).thenReturn(taskDTO);

        TaskDTO retrievedTask = taskController.getTask(1L);

        assertNotNull(retrievedTask);
        assertEquals(1L, retrievedTask.getId());
        verify(taskService, times(1)).getTask(1L);
    }

    @Test
    public void testUpdateTask() {
        TaskDTO taskDTO = new TaskDTO(1L, "Updated Task", "Updated Description", 1L, null);
        when(taskService.updateTask(eq(1L), any(TaskDTO.class))).thenReturn(taskDTO);

        TaskDTO updatedTask = taskController.updateTask(1L, taskDTO);

        assertNotNull(updatedTask);
        assertEquals("Updated Task", updatedTask.getTitle());
        verify(taskService, times(1)).updateTask(eq(1L), any(TaskDTO.class));
    }

    @Test
    public void testDeleteTask() {
        when(taskService.deleteTask(1L)).thenReturn(true);

        Boolean result = taskController.deleteTask(1L);

        assertTrue(result);
        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    public void testGetAllTasks() {
        TaskDTO task1 = new TaskDTO(1L, "Task 1", "Description 1", 1L, null);
        TaskDTO task2 = new TaskDTO(2L, "Task 2", "Description 2", 2L, null);
        List<TaskDTO> taskList = Arrays.asList(task1, task2);
        when(taskService.getAllTasks()).thenReturn(taskList);

        List<TaskDTO> tasks = taskController.getAllTasks();

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        verify(taskService, times(1)).getAllTasks();
    }
}