package com.sp.standardtaskmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sp.standardtaskmanager.constant.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Represents the task details
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "title", "description", "status", "modifiedAt"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "task details")
public class TaskDetails {

    /**
     * id of the task
     */
    @Schema(description = "id of the task")
    private UUID id;

    /**
     * title of the task
     */
    @Schema(description = "title of the task")
    private String title;

    /**
     * description of the task
     */
    @Schema(description = "description of the task")
    private String description;

    /**
     * status of the task
     */
    @Schema(description = "status of the task")
    private TaskStatus status;
}
