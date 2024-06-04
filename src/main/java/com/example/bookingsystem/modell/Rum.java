package com.example.bookingsystem.modell;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Rum {

    @Id
    @GeneratedValue
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

    @OneToMany(mappedBy = "room")
    private List<Bokning> rumBokning;


}