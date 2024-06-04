package com.example.bookingsystem.services;

import com.example.bookingsystem.dtos.BokningDto;
import com.example.bookingsystem.dtos.DetailedBokningDto;
import com.example.bookingsystem.modell.Bokning;
import com.example.bookingsystem.modell.Kund;
import com.example.bookingsystem.modell.Rum;

import java.time.LocalDate;
import java.util.List;

public interface BokningService {

    public BokningDto bokningToBokningDto(Bokning b);
    public Bokning detailedBokningToDetailedBokningDto(DetailedBokningDto b);
    public DetailedBokningDto bokningToDetailedBokningDto(Bokning b);
    public List<DetailedBokningDto> getAllBookings();

    Bokning detailedBokningDtoToBokning(DetailedBokningDto b, Kund kund, Rum rum);

    public String addBokning(DetailedBokningDto bokning);
    public String addBokningCheck(DetailedBokningDto bokning);
    public String modifyBookning(DetailedBokningDto updatedBokning);
    public String deleteBooking(Long bokningId);
    public long calculateNights(LocalDate startDate, LocalDate endDate);
    public double calculateTotalPrice(LocalDate startDate, LocalDate endDate, int pricePerNight, int nightsLastYear);
}
