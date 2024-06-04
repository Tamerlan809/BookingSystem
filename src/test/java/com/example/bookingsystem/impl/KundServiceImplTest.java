package com.example.bookingsystem.impl;

import org.example.bokningssystem.dtos.DetailedKundDto;
import org.example.bokningssystem.dtos.KundDto;
import org.example.bokningssystem.modell.Bokning;
import org.example.bokningssystem.modell.Kund;
import org.example.bokningssystem.modell.Rum;
import org.example.bokningssystem.repo.KundRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
class KundServiceImplTest {

    @MockBean
    private KundRepo kundRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KundServiceImpl kundService;

    private Kund kund;
    private Rum rum;
    private Bokning bokning;



    @BeforeEach
    void init() {
        kund = new Kund();
        rum = new Rum();
        bokning = new Bokning();
    }

    @Test
    void testKundToKundDto() {

        when(kundRepo.save(kund)).thenReturn(kund);
        KundDto kundDto = kundService.kundToKundDto(kund);

        assertEquals(kund.getId(), kundDto.getId());
        assertEquals(kund.getNamn(), kundDto.getNamn());
    }

    @Test
    void testAddKund() {
        DetailedKundDto detailedKundDto = new DetailedKundDto();

        String result = kundService.addKund(detailedKundDto);

        verify(kundRepo, times(1)).save(any());
        assertEquals("Kunden har sparats!!!!", result);
    }

    @Test
    void testModifyKund() {

            // Skapa en uppdaterad DetailedKundDto
            DetailedKundDto updatedKund = new DetailedKundDto();
            updatedKund.setId(1L);
            updatedKund.setNamn("Uppdaterad Kund");
            updatedKund.setEmail("uppdaterad@example.com");
            updatedKund.setTelefonNr("123456789");
            updatedKund.setPersonummer("123456-7890");

            // Skapa en befintlig Kund
            Kund existingKund = new Kund();
            existingKund.setId(1L);
            existingKund.setNamn("Gammal Kund");
            existingKund.setEmail("gammal@example.com");
            existingKund.setTelefonNr("0211111111");
            existingKund.setPersonummer("101112-0022");

            // Mocka kundRepo.findById()
            when(kundRepo.findById(1L)).thenReturn(Optional.of(existingKund));

            // Anropa metoden som ska testas
            String result = kundService.modifyKund(updatedKund);

            // Validera resultat
            assertEquals("Kunden har uppdaterats!", result);
            assertEquals(updatedKund.getNamn(), existingKund.getNamn());
            assertEquals(updatedKund.getEmail(), existingKund.getEmail());
            assertEquals(updatedKund.getTelefonNr(), existingKund.getTelefonNr());
            assertEquals(updatedKund.getPersonummer(), existingKund.getPersonummer());

            // Verifiera att kundRepo.save() anropas med rätt parameter
             verify(kundRepo).save(existingKund);

    }

    @Test
    void testDeleteCustomer() {

        Long customerId = 1L;

        String result = kundService.deleteCustomer(customerId);


        verify(kundRepo, times(1)).deleteById(customerId);
        assertEquals("kunden har tagit borts", result);
    }
}