package com.example.bookingsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.RequiredArgsConstructor;
import com.example.bookingsystem.configuration.IntegrationProperties;
import com.example.bookingsystem.dtos.RoomEvent;
import com.example.bookingsystem.repo.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

/*
@ComponentScan
@EnableJpaRepositories(basePackages = "org.example.bokningssystem.repo")
public class EventApplication implements CommandLineRunner {

    private String queueName = "81875e34-53a5-4077-bd4a-9f2a42ad19e6";

    @Autowired
    private EventRepo eventRepo;

    @Override
    public void run(String... args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("128.140.81.47");
        factory.setUsername("djk47589hjkew789489hjf894");
        factory.setPassword("sfdjkl54278frhj7");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            try {
                RoomEvent event = mapper.readValue(message, RoomEvent.class);
                handleEvent(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(" [x] Received '" + message + "'");
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    private void handleEvent(RoomEvent event) {

        eventRepo.save(event);

    }

}*/
@ComponentScan
@RequiredArgsConstructor
public class EventApplication implements CommandLineRunner {
    @Autowired
    private final IntegrationProperties integrationProperties;

    private final EventRepo eventRepo;

    @Override
    public void run(String... args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(integrationProperties.getEventProperties().getEventHost());
        factory.setUsername(integrationProperties.getEventProperties().getEventUsername());
        factory.setPassword(integrationProperties.getEventProperties().getEventPassword());
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            try {
                RoomEvent event = mapper.readValue(message, RoomEvent.class);
                handleEvent(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(" [x] Received '" + message + "'");
        };

        channel.basicConsume(integrationProperties.getEventProperties().getEventQueueName(), true, deliverCallback, consumerTag -> {
        });
    }

    private void handleEvent(RoomEvent event) {
        eventRepo.save(event);
    }

}