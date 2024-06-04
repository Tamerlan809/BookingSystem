package com.example.bookingsystem.impl;

import org.example.bokningssystem.dtos.BokningDto;
import org.example.bokningssystem.dtos.DetailedBokningDto;
import org.example.bokningssystem.dtos.KundDto;
import org.example.bokningssystem.dtos.RumDto;
import org.example.bokningssystem.modell.Bokning;
import org.example.bokningssystem.modell.Kund;
import org.example.bokningssystem.modell.Rum;
import org.example.bokningssystem.repo.BokningRepo;
import org.example.bokningssystem.repo.KundRepo;
import org.example.bokningssystem.repo.RumRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class BokningServiceImplTest {

 @MockBean
 private BokningRepo bokningRepo;

 @MockBean
 private KundRepo kundRepo;

 @MockBean
 private RumRepo rumRepo;

 @Autowired
 private BokningServiceImpl bokningService;

 private Rum rum;
 private Kund kund;
 private Bokning bokning;

 @BeforeEach
 void init() {
  kund = new Kund();
  kund.setId(1L);
  kund.setNamn("Test Kund");

  rum = new Rum();
  rum.setId(1L);
  rum.setName("Test Room");

  bokning = new Bokning();
  bokning.setId(1L);
  bokning.setStartDate(LocalDate.parse("2024-09-01"));
  bokning.setEndDate(LocalDate.parse("2024-09-14"));
  bokning.setKund(kund);
  bokning.setRoom(rum);
 }

 @Test
 public void testBokningToBokningDto() {
  bokning.setId(1L);
  when(bokningRepo.findById(1L)).thenReturn(Optional.of(bokning));

  BokningDto bokningDto = bokningService.bokningToBokningDto(bokning);

  assertEquals(1L, bokningDto.getId());
 }

 @Test
 public void testBokningToDetailedBokningDto() {
  when(bokningRepo.findById(1L)).thenReturn(Optional.of(bokning));
  when(kundRepo.findById(1L)).thenReturn(Optional.of(kund));
  when(rumRepo.findById(1L)).thenReturn(Optional.of(rum));

  DetailedBokningDto detailedBokningDto = bokningService.bokningToDetailedBokningDto(bokning);

  assertEquals(1L, detailedBokningDto.getId());
  assertEquals(LocalDate.parse("2024-09-01"), detailedBokningDto.getStartDate());
  assertEquals(LocalDate.parse("2024-09-14"), detailedBokningDto.getEndDate());
  assertEquals(1L, detailedBokningDto.getKund().getId());
  assertEquals("Test Kund", detailedBokningDto.getKund().getNamn());
  assertEquals(1L, detailedBokningDto.getRoom().getId());
  assertEquals("Test Room", detailedBokningDto.getRoom().getName());
 }

 @Test
 public void testAddBokning() {

  DetailedBokningDto bokningDto = new DetailedBokningDto();
  bokningDto.setStartDate(LocalDate.now().plusDays(1));
  bokningDto.setEndDate(LocalDate.now().plusDays(5));
  bokningDto.setKund(new KundDto(1L, "Testkund", "testkund@example.com", 11));
  bokningDto.setRoom(new RumDto(1L, "Testrum"));

  when(kundRepo.findById(anyLong())).thenReturn(Optional.of(kund));
  when(rumRepo.findById(anyLong())).thenReturn(Optional.of(rum));

  String result = bokningService.addBokning(bokningDto);

  assertEquals("Bokning tillagd", result);
 }

 @Test
 public void testAddBokningCheck() {
  DetailedBokningDto bokningDto = new DetailedBokningDto();
  bokningDto.setStartDate(LocalDate.now().plusDays(1));
  bokningDto.setEndDate(LocalDate.now().plusDays(5));
  bokningDto.setRoom(new RumDto(1L, "Testrum"));

  Rum rum = new Rum();
  rum.setId(1L);
  rum.setName("Testrum");

  Bokning existingBokning = new Bokning();
  existingBokning.setId(1L);
  existingBokning.setStartDate(LocalDate.now().minusDays(3));
  existingBokning.setEndDate(LocalDate.now().plusDays(3));
  existingBokning.setKund(kund);
  existingBokning.setRoom(rum);

  when(rumRepo.findById(1L)).thenReturn(Optional.of(rum));
  when(bokningRepo.findAll()).thenReturn(List.of(existingBokning));

  String result = bokningService.addBokningCheck(bokningDto);

  assertEquals("upptaget", result);
 }

 @Test
 public void testModifyBookning() {
  DetailedBokningDto updatedBokning = new DetailedBokningDto();
  updatedBokning.setId(1L);
  updatedBokning.setStartDate(LocalDate.parse("2024-09-01"));
  updatedBokning.setEndDate(LocalDate.parse("2024-09-14"));
  updatedBokning.setKund(new KundDto(1L, "Updated Kund", "updatedkund@example.com", 11));
  updatedBokning.setRoom(new RumDto(1L, "Updated Room"));

  Bokning existingBokning = new Bokning();
  existingBokning.setId(1L);
  existingBokning.setStartDate(LocalDate.parse("2024-09-01"));
  existingBokning.setEndDate(LocalDate.parse("2024-09-14"));

  Kund kund = new Kund();
  kund.setId(1L);
  kund.setNamn("Updated Kund");

  Rum rum = new Rum();
  rum.setId(1L);
  rum.setName("Updated Room");

  when(bokningRepo.findById(1L)).thenReturn(Optional.of(existingBokning));
  when(kundRepo.findById(1L)).thenReturn(Optional.of(kund));
  when(rumRepo.findById(1L)).thenReturn(Optional.of(rum));

  String result = bokningService.modifyBookning(updatedBokning);

  assertEquals("Bokningen har uppdaterats!", result);
 }

 @Test
 public void testDeleteBooking() {
  Long bokningId = 1L;

  doNothing().when(bokningRepo).deleteById(bokningId);

  String result = bokningService.deleteBooking(bokningId);

  assertEquals("kunden har tagit borts", result);
 }

 @Test
 public void testCalculateNights() {
  LocalDate startDate = LocalDate.parse("2024-09-01");
  LocalDate endDate = LocalDate.parse("2024-09-14");

  long nights = bokningService.calculateNights(startDate, endDate);

  assertEquals(13, nights);
 }

 @Test
 public void testCalculateTotalPrice_NoDiscounts() {
  LocalDate startDate = LocalDate.parse("2024-05-21");
  LocalDate endDate = LocalDate.parse("2024-05-22");
  int pricePerNight = 100;

  double totalPrice = bokningService.calculateTotalPrice(startDate, endDate, pricePerNight, 0);

  assertEquals(100, totalPrice);
 }

 @Test
 public void testCalculateTotalPrice_SundayToMondayDiscount() {
  LocalDate startDate = LocalDate.parse("2024-05-19");
  LocalDate endDate = LocalDate.parse("2024-05-20");
  int pricePerNight = 100;

  double totalPrice = bokningService.calculateTotalPrice(startDate, endDate, pricePerNight, 0);

  assertEquals(98, totalPrice);
 }

 @Test
 public void testCalculateTotalPrice_LongStayDiscount() {
  LocalDate startDate = LocalDate.parse("2024-05-20");
  LocalDate endDate = LocalDate.parse("2024-05-23");
  int pricePerNight = 100;

  double totalPrice = bokningService.calculateTotalPrice(startDate, endDate, pricePerNight, 0);

  assertEquals(298.5, totalPrice);
 }

 @Test
 public void testCalculateTotalPrice_NightsLastYearMoreThanTen() {
     LocalDate startDate = LocalDate.parse("2024-05-22");
     LocalDate endDate = LocalDate.parse("2024-05-23");
     int pricePerNight = 100;

     double totalPrice = bokningService.calculateTotalPrice(startDate, endDate, pricePerNight, 11);

     assertEquals(98, totalPrice);
 }

    @Test
    public void testCalculateTotalPrice_NightsLastYearLessThanTen() {
        LocalDate startDate = LocalDate.parse("2024-05-22");
        LocalDate endDate = LocalDate.parse("2024-05-23");
        int pricePerNight = 100;

        double totalPrice = bokningService.calculateTotalPrice(startDate, endDate, pricePerNight, 9);

        assertEquals(100, totalPrice);
    }

 @Test
 public void testCalculateTotalPrice_SundayToMondayDiscountAndLongStayDiscount() {
  LocalDate startDate = LocalDate.parse("2024-05-19");
  LocalDate endDate = LocalDate.parse("2024-05-22");
  int pricePerNight = 100;

  double totalPrice = bokningService.calculateTotalPrice(startDate, endDate, pricePerNight, 0);

  assertEquals(296.51, totalPrice);
 }
    @Test
    public void testCalculateTotalPrice_SundayToMondayDiscountAndNightsLastYearMoreThanTen() {
        LocalDate startDate = LocalDate.parse("2024-05-19");
        LocalDate endDate = LocalDate.parse("2024-05-22");
        int pricePerNight = 100;

        double totalPrice = bokningService.calculateTotalPrice(startDate, endDate, pricePerNight, 10);

        assertEquals(290.5798, totalPrice);
    }

    @Test
    public void testCalculateTotalPrice_MultiYearBooking() {
        LocalDate startDate = LocalDate.parse("2024-12-31");
        LocalDate endDate = LocalDate.parse("2025-01-01");
        int pricePerNight = 100;

        double totalPrice = bokningService.calculateTotalPrice(startDate, endDate, pricePerNight, 0);

        assertEquals(100, totalPrice);
    }
}


