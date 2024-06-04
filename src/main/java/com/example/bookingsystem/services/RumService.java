package com.example.bookingsystem.services;


import com.example.bookingsystem.dtos.*;
import com.example.bookingsystem.modell.Rum;

import java.util.List;

public interface RumService {

    public RumDto rumToRumDto(Rum r);
    public DetailedRumDto rumToDetailedRumDto(Rum r);
    public Rum detailedRumToDetailedRumDto(DetailedRumDto r);
    public List<DetailedRumDto> getAllRooms();
    public String addRum(DetailedRumDto rum);
    public List<RumDto> getAllRumSimple();
    public String addBeds(DetailedRumDto rum);
    public List<RumDto> getAvailableRum(SearchBokningDto b);
    public DetailedRumDto getRumById(Long rumId);
}