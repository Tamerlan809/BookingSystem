package com.example.bookingsystem.modell;

import jakarta.persistence.*;
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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Kund {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Måste fylla i fältet")
    @Size(min = 3, message = "Minst 3 bokstäver")
    @Pattern(regexp="^[A-Öa-ö ]*$", message = "Bara bokstäver för namn")
    private String namn;

    @NotEmpty(message = "Måste fylla i fältet")
    @Email
    private String email;

    @NotEmpty(message = "Måste fylla i fältet")
    @NumberFormat
    @Size(min = 10, max = 15)
    private String telefonNr;

    @NotEmpty(message = "Måste fylla i fältet")
    @NumberFormat
    @Size(min = 10, max = 12)
    private String personummer;

    private int nightsLastYear;

    //(mappedBy = "kund")
    @OneToMany(mappedBy = "kund", fetch = FetchType.EAGER)
    private List<Bokning> kundBokning;


    public Kund(String namn, String email, String telefonNr, String personummer, List<Bokning> kundBokning) {
        this.namn = namn;
        this.email = email;
        this.telefonNr = telefonNr;
        this.personummer = personummer;
        this.kundBokning = kundBokning;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
