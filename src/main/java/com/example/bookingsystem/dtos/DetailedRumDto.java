package com.example.bookingsystem.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.bookingsystem.modell.Rumstyp;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedRumDto {

    private Long id;
    @NotEmpty(message = "Måste fylla i fältet")
    @Size(min = 3, message = "Minst 3 bokstäver")
    private String name;


    @NotNull(message = "Rumstyp får inte vara null")
    @Enumerated(EnumType.STRING)
    private Rumstyp rumstyp;

    @Min(value = 0, message = "Måste vara minst 0")
    @Max(value = 2, message = "Måste vara högst 2")
    private int antalSangar;

    private int price;

    private List<BokningDto> bokning;
}