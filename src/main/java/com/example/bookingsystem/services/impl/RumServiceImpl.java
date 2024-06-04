package com.example.bookingsystem.services.impl;


import lombok.RequiredArgsConstructor;
import com.example.bookingsystem.dtos.*;
import com.example.bookingsystem.modell.Rum;
import com.example.bookingsystem.repo.RumRepo;
import com.example.bookingsystem.services.BokningService;
import com.example.bookingsystem.services.RumService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RumServiceImpl implements RumService {

    private final RumRepo rumRepo;
    private final BokningService bokningService;

    @Override
    public RumDto rumToRumDto(Rum r) {
        return RumDto.builder().id(r.getId()).name(r.getName()).build();
    }

    @Override
    public DetailedRumDto rumToDetailedRumDto(Rum r) {
        return DetailedRumDto.builder().id(r.getId())
                .name(r.getName()).rumstyp(r.getRumstyp()).antalSangar(r.getAntalSangar()).price(r.getPrice())
                .bokning(r.getRumBokning().stream()
                        .map(bo -> bokningService.bokningToBokningDto(bo)).toList())
                .build();
    }

    @Override
    public Rum detailedRumToDetailedRumDto(DetailedRumDto r) {
        return Rum.builder().id(r.getId())
                .name(r.getName()).rumstyp(r.getRumstyp()).antalSangar(r.getAntalSangar()).price(r.getPrice())
                .build();
    }


    @Override
    public List<RumDto> getAllRumSimple() {
        return rumRepo.findAll().stream().map(r -> rumToRumDto(r)).toList();
    }

    @Override
    public List<DetailedRumDto> getAllRooms() {
        return rumRepo.findAll().stream()
                .map(r -> rumToDetailedRumDto(r)).toList();
    }

    @Override
    public String addRum(DetailedRumDto rum) {
        rumRepo.save(detailedRumToDetailedRumDto(rum));
        return "Rum har skapats";
    }

    @Override
    public String addBeds(DetailedRumDto updatedRoom) {

        Optional<Rum> optional = rumRepo.findById(updatedRoom.getId());

        Rum existingRoom = optional.get();


        existingRoom.setName(updatedRoom.getName());
        existingRoom.setRumstyp(updatedRoom.getRumstyp());
        existingRoom.setAntalSangar(updatedRoom.getAntalSangar());
        existingRoom.setPrice(updatedRoom.getPrice());


        rumRepo.save(existingRoom);

        return "Rummet har uppdaterats!";
    }

    @Override
    public List<RumDto> getAvailableRum(SearchBokningDto searchBokningDto) {
        List<DetailedBokningDto> allBookings = bokningService.getAllBookings();

        LocalDate startDate = searchBokningDto.getStartDate();
        LocalDate endDate = searchBokningDto.getEndDate();

        List<RumDto> availableRooms = rumRepo.findAll().stream()
                .filter(rum -> isRoomAvailable(rum, allBookings, startDate, endDate))
                .map(this::rumToRumDto)
                .collect(Collectors.toList());

        return availableRooms;
    }

    private boolean isRoomAvailable(Rum rum, List<DetailedBokningDto> allBookings, LocalDate startDate, LocalDate endDate) {
        return allBookings.stream()
                .noneMatch(booking -> {
                    LocalDate bookingStartDate = booking.getStartDate();
                    LocalDate bookingEndDate = booking.getEndDate();
                    return booking.getRoom().getId().equals(rum.getId()) &&
                            !bookingEndDate.isBefore(startDate) && !endDate.isBefore(bookingStartDate);
                });
    }
    @Override
    public DetailedRumDto getRumById(Long rumId) {
        Rum rum = rumRepo.findById(rumId).orElse(null);

        return rumToDetailedRumDto(rum);
    }



}