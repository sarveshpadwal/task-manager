package com.sp.standardtaskmanager.web.controller;

import com.sp.standardtaskmanager.constant.Status;
import com.sp.standardtaskmanager.constant.TaskStatus;
import com.sp.standardtaskmanager.dto.Response;
import com.sp.standardtaskmanager.dto.TaskDetails;
import com.sp.standardtaskmanager.dto.TaskPayload;
import com.sp.standardtaskmanager.service.TaskService;
import com.sp.standardtaskmanager.service.TaskServiceImpl;
import com.sp.standardtaskmanager.web.validator.ConstraintSequence;
import com.sp.standardtaskmanager.web.validator.Exist;
import com.sp.standardtaskmanager.web.validator.ExistTaskValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

/**
 * Interface holding all the API calls for Task
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Validated(ConstraintSequence.class)
@Tag(name = "User API")
@RequestMapping("/api/v1")
public interface TaskApi {

    /**
     * @return new instance of TaskService
     */
    default TaskService getDelegate() {
        return new TaskServiceImpl(null, null, null);
    }

    /**
     * Get all tasks
     *
     * @return {@link Response} {@link List<TaskDetails>} wrapped inside {@link Response}
     */
    @Operation(description = "Get all tasks")
    @ApiResponse(
            responseCode = "200",
            description = "Success response",
            content =
            @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class, subTypes = {Response.class}),
                    examples =
                    @ExampleObject(
                            value = "{\"status\":\"SUCCESS\",\"data\":["
                                    + "{\"id\":\"9da46a6f-c2f1-4c74-a615-6a6d591b0217\","
                                    + "\"title\":\"title1\",\"status\":\"TODO\"}],\"errors\":null}"
                    )))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    default Response<List<TaskDetails>> getAllTasks(
            @RequestParam(value = "status", required = false) TaskStatus status) {
        List<TaskDetails> taskDetailsList = getDelegate().getAllTasks(status);
        return new Response<>(Status.SUCCESS, taskDetailsList);
    }

    /**
     * Get task details for given task id
     *
     * @return {@link Response} {@link TaskDetails} wrapped inside {@link Response}
     */
    @Operation(description = "Get task details for given task id")
    @ApiResponse(
            responseCode = "200",
            description = "Success response",
            content =
            @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class, subTypes = {Response.class}),
                    examples =
                    @ExampleObject(
                            value = "{\"status\":\"SUCCESS\",\"data\":{\"id\":\"9da46a6f-c2f1-4c74-a615-6a6d591b0217\","
                                    + "\"title\":\"title1\",\"status\":\"TODO\"},\"errors\":null}"
                    )))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    default Response<TaskDetails> getTask(@Exist(constraintValidator = ExistTaskValidator.class, message = "1004")
                                          @PathVariable UUID id) {
        TaskDetails taskDetails = getDelegate().getTask(id);
        return new Response<>(Status.SUCCESS, taskDetails);
    }

    /**
     * Edit task
     *
     * @param id UUID of the task
     * @return {@link Response}
     */
    @Operation(description = "Edit Task")
    @ApiResponse(
            responseCode = "200",
            description = "Success response",
            content =
            @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class, subTypes = {Response.class}),
                    examples =
                    @ExampleObject(
                            value = "{\"status\":\"SUCCESS\",\"data\":{\"id\":\"9da46a6f-c2f1-4c74-a615-6a6d591b0217\","
                                    + "\"title\":\"title1\",\"status\":\"TODO\"},\"errors\":null}"
                    )))
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/tasks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    default Response<TaskDetails> editTask(@Exist(constraintValidator = ExistTaskValidator.class, message = "1004")
                                           @PathVariable UUID id,
                                           @Valid @RequestBody TaskPayload taskPayload) {
        TaskDetails taskDetails = getDelegate().editTask(id, taskPayload);
        return new Response<>(Status.SUCCESS, taskDetails);
    }

    /**
     * Save task
     *
     * @return {@link Response}
     */
    @Operation(description = "Save Task")
    @ApiResponse(
            responseCode = "201",
            description = "Success response",
            content =
            @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class, subTypes = {Response.class}),
                    examples =
                    @ExampleObject(
                            value = "{\"status\":\"SUCCESS\",\"data\":{\"id\":\"9da46a6f-c2f1-4c74-a615-6a6d591b0217\","
                                    + "\"title\":\"title1\",\"status\":\"TODO\"},\"errors\":null}"
                    )))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    default Response<TaskDetails> saveTask(@Valid @RequestBody TaskPayload taskPayload) {
        TaskDetails taskDetails = getDelegate().saveTask(taskPayload);
        return new Response<>(Status.SUCCESS, taskDetails);
    }

    /**
     * Delete Task
     *
     * @param id UUID of the task
     * @return {@link Response}
     */
    @Operation(description = "Delete Task")
    @ApiResponse(
            responseCode = "200",
            description = "Success response",
            content =
            @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class, subTypes = {Response.class}),
                    examples =
                    @ExampleObject(
                            value = "{\"status\":\"SUCCESS\",\"data\":null,\"errors\":[]}"
                    )))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/tasks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    default Response<Void> deleteTask(@Exist(constraintValidator = ExistTaskValidator.class, message = "1004")
                                      @PathVariable UUID id) {
        getDelegate().deleteTask(id);
        return new Response<>(Status.SUCCESS);
    }
}
