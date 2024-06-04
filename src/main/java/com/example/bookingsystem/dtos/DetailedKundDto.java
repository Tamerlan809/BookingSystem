package com.example.bookingsystem.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedKundDto {
    private Long id;

    @NotEmpty(message = "Måste fylla i fältet")
    @Size(min = 3, message = "Minst 3 bokstäver")
    @Pattern(regexp="^[A-Öa-ö ]*$", message = "Du får använda bara bokstäver för namn")
    private String namn;

    @NotEmpty(message = "Måste fylla i fältet")
    @Email(message = "Du måste ha en @ i email.")
    private String email;

    @NotEmpty(message = "Måste fylla i fältet")
    @NumberFormat
    @Size(min = 10, max = 15, message = "Minst 10 och max 15 siffror")
    private String telefonNr;

    @NotEmpty(message = "Måste fylla i fältet")
    @NumberFormat
    @Size(min = 10, max = 12, message = "Minst 10 och max 12 siffror")
    private String personummer;

    private int nightsLastYear;

    private List<BokningDto> bokning;


}
