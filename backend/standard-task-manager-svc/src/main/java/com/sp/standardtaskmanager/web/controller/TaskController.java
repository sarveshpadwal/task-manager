package com.sp.standardtaskmanager.web.controller;

import com.sp.standardtaskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller for TaskApi
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@RestController
public class TaskController implements TaskApi {

    @Autowired
    private TaskService service;

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskService getDelegate() {
        return service;
    }

}
