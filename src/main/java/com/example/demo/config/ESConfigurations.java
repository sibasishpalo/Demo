package com.example.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "es")
@Getter
@Setter
public class ESConfigurations {
    private String host;
    private String protocol;
    private int port;
}
