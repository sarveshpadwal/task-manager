package com.sp.standardtaskmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfiguartion {
    @Value("${cors.allowed-origin-patterns}")
    private String allowedOrigins;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        StandardTaskManagerCorsConfiguration corsConfiguration =
                new StandardTaskManagerCorsConfiguration(allowedOrigins);
        corsConfiguration.registerCorsConfiguration("/**", new CorsConfiguration());
        return corsConfiguration;
    }
    @Bean("simpleTaskManagerCorsFilter")
    public CorsFilter corsFilter(CorsConfigurationSource corsConfigurationSource) {
        final CorsFilter corsFilter = new CorsFilter(corsConfigurationSource);
        corsFilter.setCorsProcessor(new StandardTaskManagerCorsProcessor(allowedOrigins));
        return corsFilter;
    }
}
