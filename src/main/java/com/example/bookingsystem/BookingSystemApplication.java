package com.example.bookingsystem;

import com.example.bookingsystem.security.UserDataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

@SpringBootApplication
public class BookingSystemApplication {

    @Autowired
    private UserDataSeeder userDataSeeder;

    public static void main(String[] args) {
        if (args.length == 0) {
            SpringApplication.run(BookingSystemApplication.class, args);
        } else if (Objects.equals(args[0], "FetchShippers")) {
            SpringApplication application = new SpringApplication(FetchShippers.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        } else if (Objects.equals(args[0], "ContractCustomerApplication")) {
            SpringApplication application = new SpringApplication(ContractCustomerApplication.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        }else if (Objects.equals(args[0], "EventApp")) {
            SpringApplication application  = new SpringApplication(EventApplication.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        }
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            userDataSeeder.seed();
        };
    }

}