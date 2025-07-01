package com.example.live.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.ServerSocket;

@Configuration
public class BeanConfig {

    @Bean
    public ServerSocket serverSocket() {
        try {
            // Create a ServerSocket on port 8080
            return new ServerSocket();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create ServerSocket", e);
        }
    }

}
