package com.example.bookingsystem;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import com.example.bookingsystem.configuration.IntegrationProperties;
import com.example.bookingsystem.modell.AllCustomers;
import com.example.bookingsystem.modell.ContractCustomer;
import com.example.bookingsystem.repo.ContractCustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URL;

/*@ComponentScan
public class ContractCustomerApplication implements CommandLineRunner {

    ContractCustomerRepo customerRepo;

    public ContractCustomerApplication(ContractCustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public void run(String... args) throws Exception {

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper mapper = new XmlMapper(module);
        AllCustomers customers =mapper.readValue(new URL("https://javaintegration.systementor.se/customers"),
                AllCustomers.class);


            for (ContractCustomer c : customers.customers) {
                customerRepo.save(c);
            }
    }
}*/

@Component
@RequiredArgsConstructor
public class ContractCustomerApplication implements CommandLineRunner {

    @Autowired
    private final IntegrationProperties integrationProperties;

    private final ContractCustomerRepo contractCustomerRepo;


    @Override
    public void run(String... args) throws Exception {

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper mapper = new XmlMapper(module);
        AllCustomers customers = mapper.readValue(new URL(integrationProperties
                        .getContractCustomers().getCustomersUrl()),
                         AllCustomers.class);

        for (ContractCustomer c : customers.customers) {
            contractCustomerRepo.save(c);
        }
    }
}

