package ru.t1.OpenSchoolT1.mapper;

import org.junit.jupiter.api.Test;

import ru.t1.OpenSchoolT1.dto.TaskDTO;
import ru.t1.OpenSchoolT1.model.Task;
import ru.t1.OpenSchoolT1.enums.TaskStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TaskMapperTest {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    public void testToDto() {
        Task task = new Task(1L, "Test Title", "Test Description", 1L, TaskStatus.CREATED);
        TaskDTO taskDTO = taskMapper.toDto(task);

        assertEquals(task.getId(), taskDTO.getId());
        assertEquals(task.getTitle(), taskDTO.getTitle());
        assertEquals(task.getDescription(), taskDTO.getDescription());
        assertEquals(task.getUserId(), taskDTO.getUserId());
        assertEquals(task.getStatus(), taskDTO.getStatus());
    }

    @Test
    public void testToDto_NullTask() {
        TaskDTO taskDTO = taskMapper.toDto(null);
        assertNull(taskDTO);
    }

    @Test
    public void testToEntity() {
        TaskDTO taskDTO = new TaskDTO(1L, "Test Title", "Test Description", 1L, TaskStatus.CREATED);
        Task task = taskMapper.toEntity(taskDTO);

        assertEquals(taskDTO.getId(), task.getId());
        assertEquals(taskDTO.getTitle(), task.getTitle());
        assertEquals(taskDTO.getDescription(), task.getDescription());
        assertEquals(taskDTO.getUserId(), task.getUserId());
        assertEquals(taskDTO.getStatus(), task.getStatus());
    }

    @Test
    public void testToEntity_NullTaskDTO() {
        Task task = taskMapper.toEntity(null);
        assertNull(task);
    }
}