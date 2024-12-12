package ru.t1.OpenSchoolT1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.OpenSchoolT1.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
