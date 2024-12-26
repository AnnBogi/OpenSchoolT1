package ru.t1.OpenSchoolT1.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TaskNotFoundExceptionTest {

    @Test
    public void testTaskNotFoundExceptionMessage() {
        Long taskId = 1L;

        assertThatThrownBy(() -> {
            throw new TaskNotFoundException(taskId);
        }).isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task not found with id: " + taskId);
    }
}