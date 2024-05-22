package com.sp.standardtaskmanager.web.exception;

import com.sp.standardtaskmanager.constant.Status;
import com.sp.standardtaskmanager.dto.Response;
import com.sp.standardtaskmanager.util.ErrorGenerator;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@RestController
@Hidden
@RequestMapping("${error.path:/error}")
public class NotFoundErrorController implements ErrorController {

    @Value("${error.path:/error}")
    private String errorPath;

    @GetMapping
    public ResponseEntity<Response<Void>> error(WebRequest request) {
        Response<Void> errorResponse = new Response<Void>(Status.FAIL, ErrorGenerator.generateForCode("1003"));
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
