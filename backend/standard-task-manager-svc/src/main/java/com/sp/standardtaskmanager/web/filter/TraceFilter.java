package com.sp.standardtaskmanager.web.filter;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static ch.qos.logback.core.CoreConstants.EMPTY_STRING;

@Slf4j
@RequiredArgsConstructor
@Component
public class TraceFilter extends OncePerRequestFilter {

    public static final String TRACKING_HEADER = "traceparent";
    public static final String DEFAULT = "00";
    public static final String REMOTE_IP = "remoteIp";
    public static final String REQUEST_METHOD = "requestMethod";
    public static final String REQUEST_URL = "requestUrl";
    public static final String USER_AGENT = "userAgent";
    public static final String HTTP_STATUS = "status";
    private static final Set<String> IGNORED_USER_AGENTS = Set.of("GoogleHC", "kube-probe");

    private final Tracer tracer;

    private void logRequest(HttpServletRequest request, HttpServletResponse response) {
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        if (Objects.nonNull(userAgent) && IGNORED_USER_AGENTS.stream().anyMatch(userAgent::contains)) {
            return;
        }
        String remoteIp = request.getHeader("x-forwarded-for");
        if (Objects.isNull(remoteIp)) {
            remoteIp = request.getRemoteAddr();
        } else {
            remoteIp = remoteIp.split(",")[0];
        }
        MDC.put(REMOTE_IP, remoteIp);
        MDC.put(REQUEST_METHOD, request.getMethod());
        MDC.put(REQUEST_URL, request.getRequestURI());
        MDC.put(USER_AGENT, userAgent);
        MDC.put(HTTP_STATUS, String.valueOf(response.getStatus()));
        log.warn(EMPTY_STRING);
        MDC.remove(REMOTE_IP);
        MDC.remove(REQUEST_METHOD);
        MDC.remove(REQUEST_URL);
        MDC.remove(USER_AGENT);
        MDC.remove(HTTP_STATUS);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!response.getHeaderNames().contains(TRACKING_HEADER)) {
            getCurrentTrace().ifPresent(traceparent -> {
                log.trace("added trace id in response - {}", traceparent);
                response.setHeader(TRACKING_HEADER, traceparent);
            });
        }
        filterChain.doFilter(request, response);
        logRequest(request, response);
    }

    public Optional<String> getCurrentTrace() {
        return Optional.of(tracer)
                .map(Tracer::currentSpan)
                .map(Span::context)
                .map(traceContext -> {
                    var traceId = traceContext.traceId();
                    var parentId = traceContext.spanId();
                    return DEFAULT + "-" + traceId + "-" + parentId + "-" + DEFAULT;
                });
    }
}
