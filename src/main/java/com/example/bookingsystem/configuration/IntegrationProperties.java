package com.example.bookingsystem.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
@ConfigurationPropertiesScan
@Configuration
@Data
@ConfigurationProperties(prefix = "integration")
public class IntegrationProperties {

    private ContractCustomersProperties contractCustomers;

    private FetchShippersProperties fetchShippers;

    private EventProperties eventProperties;

    private BlacklistProperties blacklistProperties;



}



