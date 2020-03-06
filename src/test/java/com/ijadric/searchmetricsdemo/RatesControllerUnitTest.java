package com.ijadric.searchmetricsdemo;

import com.ijadric.searchmetricsdemo.controllers.RateController;
import com.ijadric.searchmetricsdemo.exceptions.NoRateFoundException;
import com.ijadric.searchmetricsdemo.services.RateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static com.ijadric.searchmetricsdemo.TestUtils.prepareRates;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;

@WebMvcTest(RateController.class)
class RatesControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RateService rateService;


    @Test
    void shouldReturnLatestPrice() throws Exception {

        when(rateService.getLatestRate()).thenReturn(Double.valueOf(5000));
        this.mockMvc.perform(get("/rates/latest")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.latestPrice").value(5000));
    }

    @Test
    void latestPriceShouldBeEmpty() throws Exception {
        when(rateService.getLatestRate()).thenThrow(new NoRateFoundException());
        this.mockMvc.perform(get("/rates/latest")).andExpect(status().isNotFound());
    }

    @Test
    void statusIsOkAndListOfRatesShouldBeReturned() throws Exception {
        var rates = prepareRates();
        when(rateService.getAllRatesBetweenDates(LocalDateTime.of(2020, 3, 1, 22, 50),
                                                 LocalDateTime.of(2020, 3, 1, 22, 55)))
                .thenReturn(rates);
        this.mockMvc.perform(get("/rates?startDate=2020-03-01T22:50:00.000000&endDate=2020-03-01T22:55:00.000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rates", hasSize(3)));
    }

    @Test
    void statusIsOkAndEmptyResponse() throws Exception {
        when(rateService.getAllRatesBetweenDates(LocalDateTime.of(2020, 3, 1, 22, 50),
                LocalDateTime.of(2020, 3, 1, 22, 55)))
                .thenThrow(new NoRateFoundException());
        this.mockMvc.perform(get("/rates?startDate=2020-03-01T22:50:00.000000&endDate=2020-03-01T22:55:00.000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rates", hasSize(0)));
    }

    @Test
    void statusIsBadRequest() throws Exception{
        when(rateService.getAllRatesBetweenDates(LocalDateTime.of(2020, 3, 1, 22, 55),
                LocalDateTime.of(2020, 3, 1, 22, 50)))
                .thenThrow(new IllegalArgumentException());
        this.mockMvc.perform(get("/rates?endDate=2020-03-01T22:50:00.000000&startDate=2020-03-01T22:55:00.000000"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Start date is after end date"));
    }

    @Test
    void statusIsOkWhenNoDateIsPassed() throws Exception {
        this.mockMvc.perform(get("/rates"))
                .andExpect(status().isOk());
    }

    @Test
    void statusIs400WhenWrongParametersPassed() throws Exception {
        this.mockMvc.perform(get("/rates?endDate=test"))
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(get("/rates?startDate=notest"))
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(get("/rates?unknownParameter=test"))
                .andExpect(status().isOk()); // since an unknown parameter is not being used, it shouldn't cause harm
    }



}
