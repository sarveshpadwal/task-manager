package com.sp.standardtaskmanager.web.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class ExistValidator implements ConstraintValidator<Exist, Object> {

    @Autowired
    private ConstraintValidatorFactory constraintValidatorFactory;

    private ConstraintValidator constraintValidator; // NOSONAR

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void initialize(Exist constraintAnnotation) {
        constraintValidator = constraintValidatorFactory.getInstance(
                constraintAnnotation.constraintValidator());
        constraintValidator.initialize(constraintAnnotation);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return constraintValidator.isValid(value, context);
    }
}
