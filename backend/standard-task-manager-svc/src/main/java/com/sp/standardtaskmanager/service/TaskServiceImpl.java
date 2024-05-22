package com.sp.standardtaskmanager.service;

import com.sp.standardtaskmanager.constant.TaskStatus;
import com.sp.standardtaskmanager.dto.TaskDetails;
import com.sp.standardtaskmanager.dto.TaskPayload;
import com.sp.standardtaskmanager.entity.Task;
import com.sp.standardtaskmanager.entity.TaskAudit;
import com.sp.standardtaskmanager.exception.TaskNotFoundException;
import com.sp.standardtaskmanager.modelmapper.TaskMapper;
import com.sp.standardtaskmanager.repository.TaskAuditRepository;
import com.sp.standardtaskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repo;
    private final TaskAuditRepository auditRepo;
    private final TaskMapper mapper;

    @Override
    public List<TaskDetails> getAllTasks(TaskStatus status) {
        List<Task> tasks;
        if (Objects.nonNull(status)) {
            tasks = repo.findAllByStatus(status);
        } else {
            tasks = repo.findAll();
        }
        return tasks.stream().map(mapper::mapToDto).toList();
    }

    @Override
    public TaskDetails getTask(UUID id) {
        Task task = repo.findById(id).orElseThrow(TaskNotFoundException::new);
        return mapper.mapToDto(task);
    }

    @Override
    public TaskDetails editTask(UUID id, TaskPayload taskPayload) {
        Task task = repo.findById(id).orElseThrow(TaskNotFoundException::new);
        mapper.mergeToEntity(taskPayload, task);
        repo.save(task);
        TaskAudit taskAudit = mapper.mapToAudit(task);
        auditRepo.save(taskAudit);
        log.debug("task {} status updated to {}", id, taskPayload.getStatus());
        return mapper.mapToDto(task);
    }

    @Override
    public TaskDetails saveTask(TaskPayload taskPayload) {
        Task task = mapper.mapToEntity(taskPayload);
        task = repo.save(task);
        TaskAudit taskAudit = mapper.mapToAudit(task);
        auditRepo.save(taskAudit);
        log.debug("task {} created", taskPayload.getTitle());
        return mapper.mapToDto(task);
    }

    @Override
    public void deleteTask(UUID id) {
        repo.deleteById(id);
        log.warn("task {} deleted", id);
    }
}
