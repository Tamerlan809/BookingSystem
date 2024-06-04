package com.example.bookingsystem.services;

import com.example.bookingsystem.dtos.ContractCustomerDto;
import com.example.bookingsystem.dtos.DetailedContractCustomerDto;
import com.example.bookingsystem.modell.ContractCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContractCustomerService {



    public ContractCustomerDto contractCustomerToContractCustomerDto(ContractCustomer c);
    public DetailedContractCustomerDto contractCustomerToDetailedContractCustomerDto(ContractCustomer c);

    public Page<ContractCustomerDto> getAllCustomersSimple(Pageable pageable);
    public DetailedContractCustomerDto getCustomerById(Long customerId);
    public Page<ContractCustomerDto> getCustomers(String search, Pageable pageable);
    public Page<ContractCustomerDto> getContractCustomers(int page, int size, String sortBy, String direction, String search);
}
