package com.sp.standardtaskmanager.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final List<String> OPENAPI_ENDPOINTS = List.of(
            "/swagger-ui.html", "/api-docs.yaml", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**"
    );

    @Bean
    @Order(0)
    @ConditionalOnProperty(name = "openapi_ui_enabled", havingValue = "true")
    public SecurityFilterChain whitelistOpenApiResources(HttpSecurity http,
                                                         HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        // General server config
        return http
                .csrf(AbstractHttpConfigurer::disable)//NOSONAR
                .requestCache(AbstractHttpConfigurer::disable)
                .securityContext(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .securityMatcher(OPENAPI_ENDPOINTS.toArray(String[]::new))
                .authorizeHttpRequests(authorize -> OPENAPI_ENDPOINTS.forEach(endpoint ->
                        authorize.requestMatchers(mvcMatcherBuilder.pattern(endpoint)).permitAll()))
                .build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain secureTaskManager(HttpSecurity http) throws Exception {
        return http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)//NOSONAR
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
                .build();
    }

}
