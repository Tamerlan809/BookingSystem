package com.example.bookingsystem.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedBokningDto {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private long nights;
    private double totalPrice;
    private KundDto kund;
    private RumDto room;
}
