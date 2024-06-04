package com.example.bookingsystem.controllerTest;

import com.example.bookingsystem.controller.BokningController;
import com.example.bookingsystem.dtos.DetailedBokningDto;
import com.example.bookingsystem.dtos.KundDto;
import com.example.bookingsystem.dtos.RumDto;
import com.example.bookingsystem.repo.KundRepo;
import com.example.bookingsystem.repo.RumRepo;
import com.example.bookingsystem.services.BokningService;
import com.example.bookingsystem.services.KundService;
import com.example.bookingsystem.services.RumService;
import com.example.bookingsystem.services.impl.BlackListServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class BokningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BokningService bokningService;

    @MockBean
    private KundService kundService;

    @MockBean
    private RumService rumService;

    @MockBean
    private BlackListServiceImpl blackListService;
    @Autowired
    private RumRepo rumRepo;

    @Autowired
    private KundRepo kundRepo;

    @Test
    void testHandleBooking() {

        List<DetailedBokningDto> mockBokningar = new ArrayList<>();
        List<KundDto> mockKunder = new ArrayList<>();
        List<RumDto> mockRummen = new ArrayList<>();

        when(bokningService.getAllBookings()).thenReturn(mockBokningar);
        when(kundService.getAllKundSimple()).thenReturn(mockKunder);
        when(rumService.getAllRumSimple()).thenReturn(mockRummen);

        Model model = mock(Model.class);


        String viewName = new BokningController(bokningService, kundService, rumService, blackListService).handleBooking(model);


        assertEquals("handleBooking.html", viewName);


        verify(model).addAttribute("bokningar", mockBokningar);
        verify(model).addAttribute("kunder", mockKunder);
        verify(model).addAttribute("rummen", mockRummen);
        verify(model).addAttribute("pageTitle", "Alla befintliga bokningar");
        verify(model).addAttribute("tableHeadings", Arrays.asList("Kundens namn", "Rumsnamn", "Startdatum", "Slutdatum"));
        verify(model).addAttribute("emptyListMessage", "Inga bokningar hittades");
        verify(model).addAttribute("customerPageTitle", "Alla kunder");
        verify(model).addAttribute("customerEmptyListMessage", "Inga kunder hittades");
        verify(model).addAttribute("roomPageTitle", "Alla rum");
        verify(model).addAttribute("roomEmptyListMessage", "Inga rum hittades");
    }
}
