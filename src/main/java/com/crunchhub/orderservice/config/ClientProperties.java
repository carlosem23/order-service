package com.crunchhub.orderservice.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "crunch")
public record ClientProperties(
        @NotNull
        URI catalogServiceUri
) {
}
