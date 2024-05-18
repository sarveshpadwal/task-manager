package com.sp.simpletaskmanager.web.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {EnumValidator.class})
@Documented
public @interface ValidEnum {

    String message() default "1000";

    @SuppressWarnings("squid:S1452")
    Class<? extends Enum<?>> enumClass();

    boolean nullable() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
