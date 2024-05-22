package com.sp.standardtaskmanager.modelmapper;

import com.sp.standardtaskmanager.dto.TaskDetails;
import com.sp.standardtaskmanager.dto.TaskPayload;
import com.sp.standardtaskmanager.entity.Task;
import com.sp.standardtaskmanager.entity.TaskAudit;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * ModelMapper for Task
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class TaskMapper {

    private final ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.createTypeMap(Task.class, TaskAudit.class);
        modelMapper.createTypeMap(Task.class, TaskDetails.class);
    }

    public Task mapToEntity(TaskPayload taskPayload) {
        return modelMapper.map(taskPayload, Task.class);
    }
    public TaskAudit mapToAudit(Task task) {
        return modelMapper.map(task, TaskAudit.class);
    }

    public TaskDetails mapToDto(Task task) {
        return modelMapper.map(task, TaskDetails.class);
    }

    public void mergeToEntity(TaskPayload taskPayload, Task task) {
        modelMapper.map(taskPayload, task);
    }


}
