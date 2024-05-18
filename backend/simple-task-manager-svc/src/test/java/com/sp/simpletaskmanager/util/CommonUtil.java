package com.sp.simpletaskmanager.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.simpletaskmanager.dto.TaskPayload;
import com.sp.simpletaskmanager.entity.Task;

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
