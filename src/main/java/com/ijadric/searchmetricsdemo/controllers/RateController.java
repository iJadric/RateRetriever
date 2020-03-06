package com.ijadric.searchmetricsdemo.controllers;


import com.ijadric.searchmetricsdemo.dto.HistoricRatesDTO;
import com.ijadric.searchmetricsdemo.dto.LatestPriceDTO;
import com.ijadric.searchmetricsdemo.exceptions.NoRateFoundException;
import com.ijadric.searchmetricsdemo.services.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("rates")
public class RateController {




    private final RateService rateService;

    public RateController(@Autowired RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping("latest")
    public LatestPriceDTO getLatestPrice(){
        var latestPriceDto = new LatestPriceDTO();
        try {
            var latestPrice = rateService.getLatestRate();
            latestPriceDto.setLatestPrice(latestPrice);
            return latestPriceDto;
        }catch (NoRateFoundException nrfe){

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Latest rate can't be found");
        }
    }


    /*

    http://localhost:8080/rates?startDate=2020-03-02T19:26:50.756139Z&endDate=2020-03-02T22:50:50.000000
     */
    @GetMapping("")
    public HistoricRatesDTO getAllPrices(@RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                       @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime){
        if (startTime == null){
            startTime = LocalDateTime.of(1970,1,1,0,0);
        }
        if (endTime == null){
            endTime = LocalDateTime.now();
        }

        var historicRates = new HistoricRatesDTO();
        try {
            var rates = rateService.getAllRatesBetweenDates(startTime, endTime);
            historicRates.setRates(rates);
            return historicRates;
        } catch (IllegalArgumentException iae) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date is after end date");
        } catch (NoRateFoundException nrfe) {
            return historicRates;
        }

    }




}
