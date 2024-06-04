package com.example.bookingsystem.repo;

import com.example.bookingsystem.modell.Kund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KundRepo extends JpaRepository<Kund,Long> {
}
