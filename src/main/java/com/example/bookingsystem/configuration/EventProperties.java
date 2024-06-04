package com.example.bookingsystem.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventProperties {

    private String eventHost;
    private String eventUsername;
    private String eventPassword;
    private String eventQueueName;
}
