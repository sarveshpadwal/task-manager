package com.sp.standardtaskmanager.web.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class EnumValidator implements ConstraintValidator<ValidEnum, Object> {

    private List<String> acceptedValues = null;

    private boolean nullable;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
        nullable = constraintAnnotation.nullable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (nullable && ObjectUtils.isEmpty(value)) {
            return true;
        }
        return Objects.nonNull(value) && acceptedValues.contains(value.toString());
    }
}
