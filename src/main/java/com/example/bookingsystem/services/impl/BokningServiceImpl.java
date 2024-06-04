package com.example.bookingsystem.services.impl;


import lombok.RequiredArgsConstructor;
import com.example.bookingsystem.dtos.BokningDto;
import com.example.bookingsystem.dtos.DetailedBokningDto;
import com.example.bookingsystem.dtos.KundDto;
import com.example.bookingsystem.dtos.RumDto;
import com.example.bookingsystem.modell.Bokning;
import com.example.bookingsystem.modell.Kund;
import com.example.bookingsystem.modell.Rum;
import com.example.bookingsystem.repo.BokningRepo;
import com.example.bookingsystem.repo.KundRepo;
import com.example.bookingsystem.repo.RumRepo;
import com.example.bookingsystem.services.BokningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BokningServiceImpl implements BokningService{

    private final BokningRepo bokningRepo;
    private final KundRepo kundRepo;
    private final RumRepo rumRepo;

    @Autowired
    private EmailService emailService;


    @Override
    public BokningDto bokningToBokningDto(Bokning b) {
        return BokningDto.builder().id(b.getId()).build();
    }


    @Override
    public Bokning detailedBokningToDetailedBokningDto(DetailedBokningDto b) {
        return Bokning.builder().id(b.getId()).endDate(b.getEndDate()).startDate(b.getStartDate())
                .nights(b.getNights())
                .totalPrice(b.getTotalPrice())
                .kund(Kund.builder()
                        .id(b.getKund().getId())
                        .namn(b.getKund().getNamn())
                        .build())
                .room(Rum.builder()
                        .id(b.getRoom().getId())
                        .name(b.getRoom().getName())
                        .build())
                .build();

    }

    @Override
    public DetailedBokningDto bokningToDetailedBokningDto(Bokning b) {
        return DetailedBokningDto.builder().id(b.getId())
                .kund(new KundDto(b.getKund().getId(), b.getKund().getNamn(), b.getKund().getEmail(), b.getKund().getNightsLastYear()))
                .room(new RumDto(b.getRoom().getId(), b.getRoom().getName()))
                .startDate(b.getStartDate())
                .endDate(b.getEndDate())
                .nights(calculateNights(b.getStartDate(), b.getEndDate()))
                .totalPrice(b.getTotalPrice())
                .build();
    }

    @Override
    public List<DetailedBokningDto> getAllBookings() {
        return bokningRepo.findAll().stream()
                .map(b -> bokningToDetailedBokningDto(b)).toList();
    }

    @Override
    public Bokning detailedBokningDtoToBokning(DetailedBokningDto b, Kund kund, Rum rum){
        return Bokning.builder()
                .id(b.getId())
                .startDate(b.getStartDate())
                .endDate(b.getEndDate())
                .nights(b.getNights())
                .totalPrice(b.getTotalPrice())
                .room(rum)
                .kund(kund)
                .build();
    }

    @Override
    public String addBokning(DetailedBokningDto bokning) {
        Kund kund = kundRepo.findById(bokning.getKund().getId()).get();
        Rum rum = rumRepo.findById(bokning.getRoom().getId()).get();

        Bokning newBokning = detailedBokningDtoToBokning(bokning, kund, rum);
        newBokning.setNights(calculateNights(bokning.getStartDate(), bokning.getEndDate()));
        newBokning.setTotalPrice(calculateTotalPrice(bokning.getStartDate(), bokning.getEndDate(), rum.getPrice(), kund.getNightsLastYear()));

        bokningRepo.save(newBokning);

        emailService.sendBookingConfirmationEmail(newBokning);
        return "Bokning tillagd";
    }

    @Override
    public String addBokningCheck(DetailedBokningDto bokning) {
        Rum rum = rumRepo.findById(bokning.getRoom().getId()).get();

        List<DetailedBokningDto> bookingsForRoom = getAllBookings().stream()
                .filter(b -> b.getRoom().getId().equals(rum.getId()))
                .toList();

        boolean isDatesAvailable = bookingsForRoom.stream().noneMatch(existingBooking -> {
            boolean overlaps = !existingBooking.getEndDate().isBefore(bokning.getStartDate())
                    && !bokning.getEndDate().isBefore(existingBooking.getStartDate());
            return overlaps;
        });

        if (!isDatesAvailable) {
            return "upptaget";
        } else {
            return "fri";
        }
    }

    @Override
    public String modifyBookning(DetailedBokningDto updatedBokning) {
        Optional<Bokning> optionalBokning = bokningRepo.findById(updatedBokning.getId());
        Optional<Kund> optionalKund = kundRepo.findById(updatedBokning.getKund().getId());
        Optional<Rum> optionalRum = rumRepo.findById(updatedBokning.getRoom().getId());
        Bokning existingBokning = optionalBokning.get();
        Kund kund = optionalKund.get();
        Rum rum = optionalRum.get();

        existingBokning.setStartDate(updatedBokning.getStartDate());
        existingBokning.setEndDate(updatedBokning.getEndDate());
        existingBokning.setNights(updatedBokning.getNights());
        existingBokning.setKund(kund);
        existingBokning.setRoom(rum);
        existingBokning.setNights(calculateNights(updatedBokning.getStartDate(), updatedBokning.getEndDate()));
        existingBokning.setTotalPrice(calculateTotalPrice(updatedBokning.getStartDate(), updatedBokning.getEndDate(), rum.getPrice(), kund.getNightsLastYear()));

        bokningRepo.save(existingBokning);

        return "Bokningen har uppdaterats!";
    }

    @Override
    public String deleteBooking(Long bokningId) {
        bokningRepo.deleteById(bokningId);
        return "kunden har tagit borts";
    }

    @Override
    public long calculateNights(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    @Override
    public double calculateTotalPrice(LocalDate startDate, LocalDate endDate, int pricePerNight, int nightsLastYear) {
        long nights = calculateNights(startDate, endDate);
        double totalPrice = nights * pricePerNight;

        while (!startDate.isAfter(endDate)) {
            if (startDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                totalPrice -= pricePerNight * 0.02;
            }
            startDate = startDate.plusDays(1);
        }

        if (nights >= 2) {
            totalPrice *= 0.995;
        }

        if (nightsLastYear >= 10){
            totalPrice *= 0.98;
        }
        return totalPrice;
    }
}
