package com.example.bookingsystem.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KundDto {
    private Long id;
    private String namn;
    private String email;
    private int nightsLastYear;
    //Kan tillägga mer kanske?
}
