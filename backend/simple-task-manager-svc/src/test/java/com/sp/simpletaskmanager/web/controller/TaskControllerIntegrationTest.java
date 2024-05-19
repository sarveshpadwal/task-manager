package com.sp.simpletaskmanager.web.controller;

import com.sp.simpletaskmanager.AbstractContainerBaseTest;
import com.sp.simpletaskmanager.constant.Status;
import com.sp.simpletaskmanager.constant.TaskStatus;
import com.sp.simpletaskmanager.dto.Response;
import com.sp.simpletaskmanager.dto.TaskDetails;
import com.sp.simpletaskmanager.dto.TaskPayload;
import com.sp.simpletaskmanager.entity.Task;
import com.sp.simpletaskmanager.exception.ErrorDetails;
import com.sp.simpletaskmanager.util.CommonUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@ExtendWith(SpringExtension.class)
class TaskControllerIntegrationTest extends AbstractContainerBaseTest {
    @LocalServerPort
    private int port;

    @Autowired
    private EntityManager entityManager;

    @Sql(scripts = "classpath:/db/getAllTasks_shouldPass_whenNoStatusFilterGiven_setup.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/db/getAllTasks_shouldPass_whenNoStatusFilterGiven_teardown.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void getAllTasks_shouldPass_whenNoStatusFilterGiven() {
        /*********** Setup ************/
        String status = "";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        /*********** Execute ************/
        ResponseEntity<Response<List<TaskDetails>>> responseEntity = testRestTemplate.exchange(
                String.format("http://localhost:%d/api/v1/tasks?status=%s", port, status),
                GET, entity, new ParameterizedTypeReference<Response<List<TaskDetails>>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });
        /*********** Assertions ************/
        assertNotNull(responseEntity);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), responseEntity.getStatusCode());
        Response<List<TaskDetails>> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(Status.SUCCESS, response.getStatus());
        assertFalse(CollectionUtils.isEmpty(response.getData()));
    }

    @Sql(scripts = "classpath:/db/getAllTasks_shouldPass_whenStatusFilterGiven_setup.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/db/getAllTasks_shouldPass_whenStatusFilterGiven_teardown.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void getAllTasks_shouldPass_whenStatusFilterGiven() {
        /*********** Setup ************/
        String status = TaskStatus.TODO.getValue();
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        /*********** Execute ************/
        ResponseEntity<Response<List<TaskDetails>>> responseEntity = testRestTemplate.exchange(
                String.format("http://localhost:%d/api/v1/tasks?status=%s", port, status),
                GET, entity, new ParameterizedTypeReference<Response<List<TaskDetails>>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });
        /*********** Assertions ************/
        assertNotNull(responseEntity);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), responseEntity.getStatusCode());
        Response<List<TaskDetails>> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(Status.SUCCESS, response.getStatus());

        List<TaskDetails> taskDetailsList = response.getData();
        assertFalse(CollectionUtils.isEmpty(taskDetailsList));
        boolean allToDoTasks = taskDetailsList.stream()
                .allMatch(taskDetails -> Objects.equals(TaskStatus.TODO, taskDetails.getStatus()));
        assertTrue(allToDoTasks);

    }

    @Test
    void saveTask_shouldPass() {
        /*********** Setup ************/
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        TaskPayload taskPayload = new TaskPayload(
                "saveTask_shouldPass_title", "saveTask_shouldPass_desc", TaskStatus.TODO);
        String requestPayload = CommonUtil.writeValueAsString(taskPayload);
        HttpEntity<String> entity = new HttpEntity<>(requestPayload, headers);
        /*********** Execute ************/
        ResponseEntity<Response<TaskDetails>> responseEntity = testRestTemplate.exchange(
                String.format("http://localhost:%d/api/v1/tasks", port),
                POST, entity, new ParameterizedTypeReference<Response<TaskDetails>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });
        /*********** Assertions ************/
        assertNotNull(responseEntity);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.CREATED.value()), responseEntity.getStatusCode());
        Response<TaskDetails> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(Status.SUCCESS, response.getStatus());
        TaskDetails taskDetails = response.getData();
        assertNotNull(taskDetails.getId());
        assertEquals(taskPayload.getTitle(), taskDetails.getTitle());
        assertEquals(taskPayload.getDescription(), taskDetails.getDescription());
        assertEquals(taskPayload.getStatus(), taskDetails.getStatus());
    }

    @Test
    void saveTask_shouldFail_whenTitleNotGiven() {
        /*********** Setup ************/
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        TaskPayload taskPayload = new TaskPayload(null, "saveTask_shouldFail_desc", TaskStatus.TODO);
        String requestPayload = CommonUtil.writeValueAsString(taskPayload);
        HttpEntity<String> entity = new HttpEntity<>(requestPayload, headers);
        /*********** Execute ************/
        ResponseEntity<Response<TaskDetails>> responseEntity = testRestTemplate.exchange(
                String.format("http://localhost:%d/api/v1/tasks", port),
                POST, entity, new ParameterizedTypeReference<Response<TaskDetails>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });
        /*********** Assertions ************/
        assertNotNull(responseEntity);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), responseEntity.getStatusCode());
        Response<TaskDetails> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(Status.CLIENT_ERROR, response.getStatus());
        assertNull(response.getData());
        List<ErrorDetails> errors = response.getErrors();
        assertFalse(CollectionUtils.isEmpty(errors));
        ErrorDetails errorDetails = errors.get(0);
        assertEquals("1005", errorDetails.getCode());
        assertEquals("Please provide title of the task", errorDetails.getMessage());
    }

    @Sql(scripts = "classpath:/db/editTask_shouldPass_setup.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/db/editTask_shouldPass_teardown.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void editTask_shouldPass() {
        /*********** Setup ************/
        Task existingTask = entityManager.createQuery("FROM Task WHERE title = :title", Task.class)
                .setParameter("title", "editTask_shouldPass_title1")
                .getSingleResult();
        UUID taskId = existingTask.getId();

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        TaskPayload taskPayload = CommonUtil.mapToPayload(existingTask);
        taskPayload.setStatus(TaskStatus.INPROGRESS);
        String requestPayload = CommonUtil.writeValueAsString(taskPayload);
        HttpEntity<String> entity = new HttpEntity<>(requestPayload, headers);
        /*********** Execute ************/
        ResponseEntity<Response<TaskDetails>> responseEntity = testRestTemplate.exchange(
                String.format("http://localhost:%d/api/v1/tasks/%s", port, taskId),
                PUT, entity, new ParameterizedTypeReference<Response<TaskDetails>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });
        /*********** Assertions ************/
        assertNotNull(responseEntity);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), responseEntity.getStatusCode());
        Response<TaskDetails> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(Status.SUCCESS, response.getStatus());
        TaskDetails taskDetails = response.getData();
        assertEquals(taskId, taskDetails.getId());
        assertEquals(taskPayload.getTitle(), taskDetails.getTitle());
        assertEquals(taskPayload.getDescription(), taskDetails.getDescription());
        assertEquals(taskPayload.getStatus(), taskDetails.getStatus());

        TaskStatus updatedTaskStatus = entityManager.createQuery("SELECT status FROM Task WHERE id = :id",
                        TaskStatus.class)
                .setParameter("id", taskId)
                .getSingleResult();
        assertEquals(taskPayload.getStatus(), updatedTaskStatus);
    }

    @Sql(scripts = "classpath:/db/deleteTask_shouldPass_setup.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/db/deleteTask_shouldPass_teardown.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void deleteTask_shouldPass() {
        /*********** Setup ************/
        UUID existingTaskId = entityManager.createQuery("SELECT id FROM Task WHERE title = :title", UUID.class)
                .setParameter("title", "deleteTask_shouldPass_title1")
                .getSingleResult();

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        /*********** Execute ************/
        ResponseEntity<Response<Void>> responseEntity = testRestTemplate.exchange(
                String.format("http://localhost:%d/api/v1/tasks/%s", port, existingTaskId),
                DELETE, entity, new ParameterizedTypeReference<Response<Void>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });
        /*********** Assertions ************/
        assertNotNull(responseEntity);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), responseEntity.getStatusCode());
        Response<Void> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(Status.SUCCESS, response.getStatus());
        assertNull(response.getData());

        Long count = entityManager.createQuery("SELECT COUNT(1) FROM Task WHERE id = :id", Long.class)
                .setParameter("id", existingTaskId)
                .getSingleResult();
        assertEquals(0, count);
    }

    @Test
    void deleteTask_shouldFail_whenTaskNotFound() {
        /*********** Setup ************/
        UUID existingTaskId = UUID.randomUUID();

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        /*********** Execute ************/
        ResponseEntity<Response<Void>> responseEntity = testRestTemplate.exchange(
                String.format("http://localhost:%d/api/v1/tasks/%s", port, existingTaskId),
                DELETE, entity, new ParameterizedTypeReference<Response<Void>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });
        /*********** Assertions ************/
        assertNotNull(responseEntity);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), responseEntity.getStatusCode());
        Response<Void> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(Status.CLIENT_ERROR, response.getStatus());
        assertNull(response.getData());
        List<ErrorDetails> errors = response.getErrors();
        assertFalse(CollectionUtils.isEmpty(errors));
        ErrorDetails errorDetails = errors.get(0);
        assertEquals("1004", errorDetails.getCode());
        assertEquals("Task not found", errorDetails.getMessage());
    }
}
