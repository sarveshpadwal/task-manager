package com.sp.standardtaskmanager.service;

import com.sp.standardtaskmanager.constant.TaskStatus;
import com.sp.standardtaskmanager.dto.TaskDetails;
import com.sp.standardtaskmanager.dto.TaskPayload;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    List<TaskDetails> getAllTasks(TaskStatus status);

    TaskDetails getTask(UUID id);

    TaskDetails editTask(UUID id, TaskPayload taskPayload);

    TaskDetails saveTask(TaskPayload taskPayload);

    void deleteTask(UUID id);
}
