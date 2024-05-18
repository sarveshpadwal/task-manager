package com.sp.simpletaskmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sp.simpletaskmanager.constant.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the Task Payload
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"title", "description", "status"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "task details to save in the database")
public class TaskPayload {

    /**
     * title of the task
     */
    @NotEmpty(message = "1005")
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
    @NotNull(message = "1006")
    @Schema(description = "status of the task")
    private TaskStatus status;
}
