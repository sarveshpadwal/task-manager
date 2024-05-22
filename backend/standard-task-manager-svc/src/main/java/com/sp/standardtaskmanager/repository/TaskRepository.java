package com.sp.standardtaskmanager.repository;

import com.sp.standardtaskmanager.constant.TaskStatus;
import com.sp.standardtaskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findAllByStatus(TaskStatus status);
}
