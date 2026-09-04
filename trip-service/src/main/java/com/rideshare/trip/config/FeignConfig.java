package com.rideshare.trip.config;

import feign.Logger;
import feign.codec.ErrorDecoder;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    ErrorDecoder errorDecoder() {
        org.slf4j.Logger log = LoggerFactory.getLogger("NotificationFeign");
        return (methodKey, response) -> {
            log.warn("Notification service returned {} for {}", response.status(), methodKey);
            return new RuntimeException("Notification service unavailable");
        };
    }
}
