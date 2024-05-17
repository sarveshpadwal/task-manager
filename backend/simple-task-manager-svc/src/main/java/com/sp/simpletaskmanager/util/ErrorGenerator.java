package com.sp.simpletaskmanager.util;

import com.sp.simpletaskmanager.exception.ErrorDetails;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;


/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@SuppressWarnings("squid:S2637")
public final class ErrorGenerator {

    private static final String DEFAULT_MESSAGE = "Unknown error. Please contact support";

    private static MessageSource messageSource;

    private ErrorGenerator() {
        throw new AssertionError();
    }

    public static void setErrorMessageSource(MessageSource errorMessageSource) {
        messageSource = errorMessageSource;
    }

    public static ErrorDetails generateForCode(String code) {
        return new ErrorDetails(
                code,
                messageSource
                        .getMessage(
                                code,
                                null,
                                DEFAULT_MESSAGE,
                                LocaleContextHolder.getLocale()
                        )
        );
    }

    public static ErrorDetails generateForCodeWithTarget(String code, String target) {
        return new ErrorDetails(
                code,
                messageSource
                        .getMessage(
                                code,
                                null,
                                DEFAULT_MESSAGE,
                                LocaleContextHolder.getLocale()
                        ),
                target
        );
    }

    public static ErrorDetails generateForCodeWithArguments(String code, Object... args) {
        return new ErrorDetails(
                code,
                messageSource
                        .getMessage(
                                code,
                                args,
                                DEFAULT_MESSAGE,
                                LocaleContextHolder.getLocale()
                        )
        );
    }

    public static ErrorDetails generateForCodeWithArgumentsAndTarget(String code, String target, Object... args) {
        return new ErrorDetails(
                code,
                messageSource.getMessage(
                        code,
                        args,
                        DEFAULT_MESSAGE,
                        LocaleContextHolder.getLocale()
                ),
                target
        );
    }
}
