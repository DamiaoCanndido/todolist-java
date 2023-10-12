package br.com.damiao.todolist.task;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<TaskEntity, UUID> {
    List<TaskEntity> findByUserId(UUID userId);
}
