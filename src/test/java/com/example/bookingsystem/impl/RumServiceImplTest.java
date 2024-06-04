package com.example.bookingsystem.impl;

import org.example.bokningssystem.dtos.BokningDto;
import org.example.bokningssystem.dtos.DetailedRumDto;
import org.example.bokningssystem.dtos.RumDto;
import org.example.bokningssystem.modell.Bokning;
import org.example.bokningssystem.modell.Kund;
import org.example.bokningssystem.modell.Rum;
import org.example.bokningssystem.repo.BokningRepo;
import org.example.bokningssystem.repo.KundRepo;
import org.example.bokningssystem.repo.RumRepo;
import org.example.bokningssystem.services.RumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@Nested
@SpringBootTest
@AutoConfigureMockMvc
class RumServiceImplTest {
    @MockBean
    private BokningRepo bokningRepo;

    @MockBean
    private KundRepo kundRepo;

    @MockBean
    private RumRepo rumRepo;

    @Autowired
    private RumService rumService;



    private Rum rum;
    private Kund kund;
    private Bokning bokning;

    @BeforeEach
    void init() {
        kund = new Kund();
        rum = new Rum();
        bokning = new Bokning();
    }

    @Test
    void testRumToRumDto() {

        rum.setId(1L);
        rum.setName("Rum 101");

        // Anropa rumToRumDto-metoden
        RumDto rumDto = rumService.rumToRumDto(rum);

        // Validera resultatet
        assertEquals(rum.getId(), rumDto.getId()); // Validera id
        assertEquals(rum.getName(), rumDto.getName());
        }

    @Test
    void testRumToDetailedRumDto() {

        rum.setId(1L);
        rum.setName("Rum 101");


        Bokning bokning1 = new Bokning();
        bokning1.setId(1L);
        Bokning bokning2 = new Bokning();
        bokning2.setId(2L);


        if (rum.getRumBokning() == null) {
            rum.setRumBokning(new ArrayList<>());
        }

        rum.getRumBokning().add(bokning1);
        rum.getRumBokning().add(bokning2);


        DetailedRumDto detailedRumDto = rumService.rumToDetailedRumDto(rum);


        assertEquals(rum.getId(), detailedRumDto.getId());
        assertEquals(rum.getName(), detailedRumDto.getName());


        List<BokningDto> bokningDtos = detailedRumDto.getBokning();
        assertEquals(rum.getRumBokning().size(), bokningDtos.size());

    }

}