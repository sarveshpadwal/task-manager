package com.sp.standardtaskmanager.web.validator;

import com.sp.standardtaskmanager.repository.TaskRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class ExistTaskValidator implements ConstraintValidator<Exist, UUID> {

    @Autowired
    private TaskRepository taskRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext context) {
        return taskRepository.existsById(id);
    }

}
