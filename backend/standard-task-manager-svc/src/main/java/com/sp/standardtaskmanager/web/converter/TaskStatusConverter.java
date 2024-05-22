package com.sp.standardtaskmanager.web.converter;

import com.sp.standardtaskmanager.constant.TaskStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskStatusConverter implements Converter<String, TaskStatus> {
    @Override
    public TaskStatus convert(String status) {
        return TaskStatus.fromStatus(status);
    }
}
