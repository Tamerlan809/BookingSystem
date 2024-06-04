package com.example.bookingsystem;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import com.example.bookingsystem.configuration.IntegrationProperties;
import com.example.bookingsystem.modell.Shippers;
import com.example.bookingsystem.repo.ShippersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

/*@ComponentScan
public class FetchShippers implements CommandLineRunner {

    private final ShippersRepo shippersRepo;

    public FetchShippers(ShippersRepo shippersRepo) {
        this.shippersRepo = shippersRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        JsonMapper jsonMapper = new JsonMapper();
        Shippers[] theShippers = jsonMapper.readValue(
                new URL("https://javaintegration.systementor.se/shippers"),
                Shippers[].class
        );

        for (Shippers shipper : theShippers) {
            shippersRepo.save(shipper);
        }
    }
}*/


@ComponentScan
@RequiredArgsConstructor
public class FetchShippers implements CommandLineRunner {

    @Autowired
    private final IntegrationProperties integrationProperties;

    private final ShippersRepo shippersRepo;


    @Override
    public void run(String... args) throws Exception {
        JsonMapper jsonMapper = new JsonMapper();
        Shippers[] theShippers = jsonMapper.readValue(
                new URL(integrationProperties.getFetchShippers()
                        .getShippersUrl()),
                Shippers[].class
        );

        for (Shippers shipper : theShippers) {
            shippersRepo.save(shipper);
        }
    }
}

