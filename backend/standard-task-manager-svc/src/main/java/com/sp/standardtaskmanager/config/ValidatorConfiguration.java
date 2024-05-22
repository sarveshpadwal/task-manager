package com.sp.standardtaskmanager.config;

import jakarta.validation.ConstraintValidatorFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class ValidatorConfiguration {
    @Bean
    public ConstraintValidatorFactory constraintValidatorFactory(
            AutowireCapableBeanFactory beanFactory) {
        return new SpringConstraintValidatorFactory(beanFactory);
    }
}
