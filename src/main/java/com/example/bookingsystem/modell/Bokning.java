package com.example.bookingsystem.modell;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Bokning {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;

    private long nights;
    private double totalPrice;

    @ManyToOne
    @JoinColumn
    private Rum room;

    @ManyToOne
    @JoinColumn
    private Kund kund;

}

