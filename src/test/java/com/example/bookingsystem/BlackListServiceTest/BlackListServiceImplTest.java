package com.example.bookingsystem.BlackListServiceTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.bookingsystem.configuration.IntegrationProperties;
import com.example.bookingsystem.services.impl.BlackListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BlackListServiceImplTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private IntegrationProperties integrationProperties;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIsCustomerBlacklisted_CustomerNotBlacklisted() throws Exception {

        String email = "test@example.com";
        String responseBody = "[]";
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.body()).thenReturn(responseBody);
        when(httpClient.send(Mockito.any(), Mockito.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockResponse);


       BlackListServiceImpl blackListService = new BlackListServiceImpl(integrationProperties,httpClient, objectMapper);
       boolean isBlacklisted = blackListService.isCustomerBlacklisted(email);


       assertFalse(isBlacklisted);
    }

    @Test
    public void testAddEmailToBlackList() throws Exception {
        // Arrange
        String email = "test@example.com";
        String responseBody = "{\"email\":\"test@example.com\",\"ok\":true}";
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.body()).thenReturn(responseBody);
        when(httpClient.send(Mockito.any(), Mockito.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockResponse);

      BlackListServiceImpl blackListService = new BlackListServiceImpl(integrationProperties,httpClient, objectMapper);
      blackListService.addEmailToBlackList(email);


    }

    @Test
    public void testRemoveEmailFromBlackList() throws Exception {
        // Arrange
        String email = "test@example.com";
        boolean newStatus = true;
        String responseBody = "{\"email\":\"test@example.com\",\"ok\":true}";
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.body()).thenReturn(responseBody);
        when(httpClient.send(Mockito.any(), Mockito.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockResponse);

    BlackListServiceImpl blackListService = new BlackListServiceImpl(integrationProperties,httpClient, objectMapper);
    blackListService.removeEmailFromBlackList(email, newStatus);


    }
}
