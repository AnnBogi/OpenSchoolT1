package ru.t1.OpenSchoolT1.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.t1.OpenSchoolT1.dto.TaskDTO;
import ru.t1.OpenSchoolT1.enums.TaskStatus;
import ru.t1.OpenSchoolT1.exception.TaskNotFoundException;
import ru.t1.OpenSchoolT1.mapper.TaskMapper;
import ru.t1.OpenSchoolT1.model.Task;
import ru.t1.OpenSchoolT1.repository.TaskRepository;
import ru.t1.OpenSchoolT1.kafka.KafkaTaskProducer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private KafkaTaskProducer kafkaTaskProducer;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTask() {
        TaskDTO taskDTO = new TaskDTO(null, "Test Title", "Test Description", 1L, null);
        Task task = new Task();
        when(taskMapper.toEntity(taskDTO)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(taskDTO);

        TaskDTO createdTaskDTO = taskService.createTask(taskDTO);

        assertNotNull(createdTaskDTO);
        verify(taskRepository).save(task);
    }

    @Test
    public void testGetTask_Success() {
        Long taskId = 1L;
        Task task = new Task(taskId, "Test Title", "Test Description", 1L, null);
        TaskDTO taskDTO = new TaskDTO(taskId, "Test Title", "Test Description", 1L, null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(taskDTO);

        TaskDTO result = taskService.getTask(taskId);

        assertEquals(taskDTO, result);
    }

    @Test
    public void testGetTask_NotFound() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(taskId));
    }

    @Test
    public void testUpdateTask_Success() {
        Long taskId = 1L;
        TaskDTO taskDTO = new TaskDTO(taskId, "Updated Title", "Updated Description", 1L,TaskStatus.CREATED);
        Task existingTask = new Task(taskId, "Old Title", "Old Description", 1L, TaskStatus.CREATED);
        Task updatedTask = new Task(taskId, "Updated Title", "Updated Description", 1L, TaskStatus.CREATED);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskMapper.toEntity(taskDTO)).thenReturn(updatedTask);
        when(taskRepository.save(existingTask)).thenReturn(updatedTask);
        when(taskMapper.toDto(updatedTask)).thenReturn(taskDTO);

        TaskDTO result = taskService.updateTask(taskId, taskDTO);

        assertEquals(taskDTO, result);
        verify(kafkaTaskProducer).sendTaskStatusUpdate(taskDTO);
    }

    @Test
    public void testUpdateTask_NotFound() {
        Long taskId = 1L;
        TaskDTO taskDTO = new TaskDTO(taskId, "Updated Title", "Updated Description", 1L, null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(taskId, taskDTO));
    }

    @Test
    public void testDeleteTask_Success() {
        Long taskId = 1L;

        when(taskRepository.existsById(taskId)).thenReturn(true);

        boolean result = taskService.deleteTask(taskId);

        assertTrue(result);
        verify(taskRepository).deleteById(taskId);
    }

    @Test
    public void testDeleteTask_NotFound() {
        Long taskId = 1L;

        when(taskRepository.existsById(taskId)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(taskId));
    }

    @Test
    public void testGetAllTasks() {
        Task task1 = new Task(1L, "Task 1", "Description 1", 1L, TaskStatus.CREATED);
        Task task2 = new Task(2L, "Task 2", "Description 2", 2L, TaskStatus.UPDATED);
        TaskDTO taskDTO1 = new TaskDTO(1L, "Task 1", "Description 1", 1L, TaskStatus.CREATED);
        TaskDTO taskDTO2 = new TaskDTO(2L, "Task 2", "Description 2", 2L, TaskStatus.UPDATED);

        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));
        when(taskMapper.toDto(task1)).thenReturn(taskDTO1);
        when(taskMapper.toDto(task2)).thenReturn(taskDTO2);

        List<TaskDTO> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        assertEquals(taskDTO1, result.get(0));
        assertEquals(taskDTO2, result.get(1));
    }
}