package com.sp.simpletaskmanager.service;

import com.sp.simpletaskmanager.dto.TaskDetails;
import com.sp.simpletaskmanager.dto.TaskPayload;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    List<TaskDetails> getAllTasks();

    TaskDetails getTask(UUID id);

    TaskDetails editTask(UUID id, TaskPayload taskPayload);

    TaskDetails saveTask(TaskPayload taskPayload);

    void deleteTask(UUID id);
}
