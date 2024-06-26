package com.sp.standardtaskmanager.config;

import com.sp.standardtaskmanager.util.ErrorGenerator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class ErrorMessageSourceConfiguration {

    @Bean
    public MessageSource errorMessageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:errors-list");
        messageSource.setDefaultEncoding("UTF-8");

        ErrorGenerator.setErrorMessageSource(messageSource);

        return messageSource;
    }
}
