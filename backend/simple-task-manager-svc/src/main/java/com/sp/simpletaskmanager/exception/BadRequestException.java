package com.sp.simpletaskmanager.exception;

import com.sp.simpletaskmanager.util.ErrorGenerator;

/**
 * This exception is thrown for api validations
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
public class BadRequestException extends TaskManagerException {

    /**
     * @see TaskManagerException#TaskManagerException(ErrorDetails)
     */
    public BadRequestException(String code) {
        super(ErrorGenerator.generateForCode(code));
    }
}
