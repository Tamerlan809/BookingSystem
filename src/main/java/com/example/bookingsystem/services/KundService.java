package com.example.bookingsystem.services;

import com.example.bookingsystem.dtos.DetailedKundDto;
import com.example.bookingsystem.dtos.KundDto;
import com.example.bookingsystem.modell.Kund;

import java.util.List;

public interface KundService {

    public KundDto kundToKundDto(Kund k);
    public DetailedKundDto kundToDetailedKundDto(Kund k);
    public Kund detailedKundToDetailedKundDto(DetailedKundDto k);
    public List<DetailedKundDto> getAllKunder();
    public List<KundDto> getAllKundSimple();

    public String addKund(DetailedKundDto kund);
    public String modifyKund(DetailedKundDto updatedKund);
    public String deleteCustomerCheck(Long customerId);
    public String deleteCustomer(Long customerId);
    public void updateNightsLastYearForAllCustomers();
}
