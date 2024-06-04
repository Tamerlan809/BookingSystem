package com.example.bookingsystem.controllerTest;

import com.example.bookingsystem.controller.RumController;
import com.example.bookingsystem.services.RumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@WebMvcTest(RumController.class)
public class RumControllerTest {

    @MockBean
    private RumService rumService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void handleRoomsTest() throws Exception {
        when(rumService.getAllRooms()).thenReturn(new ArrayList<>());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/rooms"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("handleRooms.html"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("rummen"))
                .andExpect(MockMvcResultMatchers.model().attribute("pageTitle", "Alla befintliga rum"))
                .andExpect(MockMvcResultMatchers.model().attribute("emptyListMessage", "Inga rum hittades"));
    }
}
