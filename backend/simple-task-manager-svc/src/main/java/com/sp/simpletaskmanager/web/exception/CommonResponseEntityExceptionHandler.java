package com.sp.simpletaskmanager.web.exception;

import com.sp.simpletaskmanager.constant.Status;
import com.sp.simpletaskmanager.dto.Response;
import com.sp.simpletaskmanager.exception.ErrorDetails;
import com.sp.simpletaskmanager.util.ErrorGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Set;

/**
 * Common exception handler to handle generic rest exceptions
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@SuppressWarnings({"squid:S1192", "squid:S2293"})
@Slf4j
@RestControllerAdvice
public class CommonResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("CLIENT_ERROR", ex);
        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
        if (!CollectionUtils.isEmpty(supportedMethods)) {
            headers.setAllow(supportedMethods);
        }
        Response<Void> errorResponse = new Response<Void>(Status.CLIENT_ERROR, ErrorGenerator.generateForCode("1001"));
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        log.error("CLIENT_ERROR", ex);
        Response<Void> errorResponse = new Response<Void>(Status.CLIENT_ERROR, ErrorGenerator.generateForCode("1003"));
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("CLIENT_ERROR", ex);
        Response<Void> errorResponse = new Response<Void>(Status.CLIENT_ERROR, ErrorGenerator.generateForCode("1002"));
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleServletRequestBindingException(
            ServletRequestBindingException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("CLIENT_ERROR", ex);
        Response<Void> errorResponse = new Response<Void>(Status.CLIENT_ERROR, ErrorGenerator.generateForCode("1002"));
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("CLIENT_ERROR", ex);
        Response<Void> errorResponse = new Response<Void>(Status.CLIENT_ERROR, ErrorGenerator.generateForCode("1002"));
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("CLIENT_ERROR", ex);
        Response<Void> errorResponse = new Response<Void>(Status.CLIENT_ERROR, ErrorGenerator.generateForCode("1002"));
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("CLIENT_ERROR", ex);
        Response<Void> errorResponse;
        if (ex.getBindingResult().hasFieldErrors()) {
            ErrorDetails[] arr =
                    ex.getBindingResult().getFieldErrors()
                            .stream().map(error -> ErrorGenerator.generateForCode(error.getDefaultMessage()))
                            .toArray(ErrorDetails[]::new);
            errorResponse = new Response<Void>(Status.CLIENT_ERROR, arr);
        } else {
            errorResponse = new Response<Void>(Status.CLIENT_ERROR, ErrorGenerator.generateForCode("1002"));
        }

        return new ResponseEntity<>(errorResponse, headers, status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("CLIENT_ERROR", ex);
        Response<Void> errorResponse = new Response<Void>(Status.CLIENT_ERROR, ErrorGenerator.generateForCode("1002"));
        return new ResponseEntity<>(errorResponse, headers, status);
    }
}
