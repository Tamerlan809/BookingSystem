package com.example.bookingsystem.controllerTest;

import com.example.bookingsystem.controller.KundController;
import com.example.bookingsystem.dtos.DetailedKundDto;
import com.example.bookingsystem.services.KundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KundController.class)
public class KundControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private KundService kundService;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new KundController(kundService)).build();
    }

    @Test
    void handleCustomerTest() throws Exception {
        when(kundService.getAllKunder()).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/customer"))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("handleCustomer.html"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("kunder"))
                .andExpect(MockMvcResultMatchers.model().attribute("pageTitle", "Alla befintliga kunder"))
                .andExpect(MockMvcResultMatchers.model().attribute("emptyListMessage", "Inga kunder hittades"));
    }


    @Test
    public void modifyCustomerTest() throws Exception {
        DetailedKundDto kund = new DetailedKundDto();
        kund.setId(1L);
        kund.setNamn("Modified Kund");
        kund.setEmail("modified@example.com");
        kund.setTelefonNr("987654321");
        kund.setPersonummer("987654-3210");

        mockMvc.perform(post("/modifyCustomer")
                        .flashAttr("kund", kund))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/customer"))
                .andExpect(flash().attributeExists("errors"));

        verify(kundService, times(0)).modifyKund(any(DetailedKundDto.class));
    }



    @Test
    void deleteByIdCheck_KopplingMessageTest() throws Exception {

        long customerId = 1L;
        when(kundService.deleteCustomerCheck(customerId)).thenReturn("koppling");


        mockMvc.perform(MockMvcRequestBuilders.get("/customer/deleteById/{id}", customerId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer"))
                .andExpect(flash().attribute("kundFinnsBokad", "Kunden är kopplad till en bokning"));


        verify(kundService, times(1)).deleteCustomerCheck(customerId);
        verify(kundService, never()).deleteCustomer(customerId);
    }
}
