package com.sp.simpletaskmanager.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * Enum holding all possible status of task
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
public enum TaskStatus {
    ALL("All"), TODO("To Do"), INPROGRESS("In Progress"), DONE("Done");

    @JsonValue
    @Getter
    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static TaskStatus fromStatus(String status) {
        return Stream.of(values())
                .filter(taskStatus -> taskStatus.getValue().equals(status))
                .findAny()
                .orElse(null);
    }

    @Override
    public String toString() {
        return value;
    }
}
