package com.sp.standardtaskmanager.exception;

import com.sp.standardtaskmanager.util.ErrorGenerator;

/**
 * This exception is thrown when a particular Task is Not found
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */

public class TaskNotFoundException extends TaskManagerException {

    /**
     * @see TaskManagerException#TaskManagerException(ErrorDetails)
     */
    public TaskNotFoundException() {
        super(ErrorGenerator.generateForCode("1004"));
    }
}
