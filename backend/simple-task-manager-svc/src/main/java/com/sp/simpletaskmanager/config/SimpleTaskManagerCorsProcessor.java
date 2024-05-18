package com.sp.simpletaskmanager.config;

import com.sp.simpletaskmanager.web.filter.TraceFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.owasp.esapi.ESAPI;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.DefaultCorsProcessor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.sp.simpletaskmanager.config.SimpleTaskManagerCorsConfiguration.ALLOWED_HEADERS;
import static com.sp.simpletaskmanager.config.SimpleTaskManagerCorsConfiguration.CORS_MAX_AGE_IN_SEC;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_MAX_AGE;

@Slf4j
public class SimpleTaskManagerCorsProcessor extends DefaultCorsProcessor {

    private final List<String> allowedOrigins;

    public SimpleTaskManagerCorsProcessor(String allowedUrls) {
        allowedOrigins = Optional.ofNullable(allowedUrls)
                .map(urls -> Arrays.stream(urls.split(","))
                        .toList())
                .orElse(Collections.emptyList());
        log.warn("configured origins {}", allowedOrigins);
    }

    @Override
    public boolean processRequest(CorsConfiguration configuration,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws IOException {
        String origin = request.getHeader("origin");
        boolean hasAllowedOrigin = Optional.ofNullable(origin)
                .map(headerValue -> allowedOrigins.contains("*") || allowedOrigins.stream()
                        .anyMatch(allowedOrigin -> allowedOrigin.equals(headerValue)))
                .orElse(false);

        if (hasAllowedOrigin) {
            ESAPI.httpUtilities().setHeader(response, ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        }
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH");
        response.setHeader(ACCESS_CONTROL_MAX_AGE, String.valueOf(CORS_MAX_AGE_IN_SEC));
        response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, String.join(",", ALLOWED_HEADERS));
        response.setHeader(ACCESS_CONTROL_EXPOSE_HEADERS, TraceFilter.TRACKING_HEADER);
        return super.processRequest(configuration, request, response);
    }
}
