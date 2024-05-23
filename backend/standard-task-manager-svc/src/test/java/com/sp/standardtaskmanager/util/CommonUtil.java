package com.sp.standardtaskmanager.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.standardtaskmanager.dto.TaskPayload;
import com.sp.standardtaskmanager.entity.Task;

public final class CommonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String writeValueAsString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static TaskPayload mapToPayload(Task task) {
        return new TaskPayload(task.getTitle(), task.getDescription(), task.getStatus());
    }
}
