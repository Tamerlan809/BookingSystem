package com.example.bookingsystem.repo;

import com.example.bookingsystem.modell.ContractCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractCustomerRepo extends JpaRepository<ContractCustomer, Long> {
    Page<ContractCustomer> findByCompanyNameContainingIgnoreCaseOrContactNameContainingIgnoreCaseOrCountryContainingIgnoreCase(
            String companyName, String contactName, String country, Pageable pageable);

}
