package com.sp.simpletaskmanager.web.exception;

import com.sp.simpletaskmanager.constant.Status;
import com.sp.simpletaskmanager.dto.Response;
import com.sp.simpletaskmanager.exception.BadRequestException;
import com.sp.simpletaskmanager.exception.ErrorDetails;
import com.sp.simpletaskmanager.exception.TaskManagerException;
import com.sp.simpletaskmanager.exception.TaskNotFoundException;
import com.sp.simpletaskmanager.util.ErrorGenerator;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler to handle API specific exceptions.
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Slf4j
@RestControllerAdvice
public class TaskManagerExceptionHandler extends CommonResponseEntityExceptionHandler {

    @SuppressWarnings({"rawtypes"})
    @ExceptionHandler({DataIntegrityViolationException.class})
    public final ResponseEntity<Response> handleDataIntegrityViolationException(DataIntegrityViolationException dex) {
        log.error("CLIENT_ERROR", dex);
        Response errorResponse = new Response<Void>(Status.CLIENT_ERROR,
                ErrorGenerator.generateForCode("1000"));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings({"rawtypes"})
    @ExceptionHandler({ConstraintViolationException.class})
    public final ResponseEntity<Response> handleConstraintViolationException(ConstraintViolationException ax) {
        log.error("CLIENT_ERROR", ax);
        ErrorDetails[] arr =
                ax.getConstraintViolations().stream()
                        .map(error -> ErrorGenerator.generateForCode(error.getMessage()))
                        .toArray(ErrorDetails[]::new);
        Response errorResponse = new Response<Void>(Status.CLIENT_ERROR, arr);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings({"rawtypes"})
    @ExceptionHandler({TaskNotFoundException.class, BadRequestException.class})
    public final ResponseEntity<Response> handleClientErrors(TaskManagerException ex) {
        log.error("CLIENT_ERROR", ex);
        Response errorResponse = new Response<Void>(Status.CLIENT_ERROR, ex.getError());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings({"rawtypes"})
    @ExceptionHandler({TaskManagerException.class})
    public final ResponseEntity<Response> handleTaskManagerException(TaskManagerException ax) {
        log.error("TaskManagerException", ax);
        Response errorResponse =
                new Response<Void>(Status.FAIL, ax.getError());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SuppressWarnings({"rawtypes"})
    @ExceptionHandler({Exception.class})
    public final ResponseEntity<Response> handleExceptions(Exception ex) {
        log.error("Exception", ex);
        Response errorResponse = new Response<Void>(Status.FAIL, ErrorGenerator.generateForCode("1000"));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
