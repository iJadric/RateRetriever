package com.ijadric.searchmetricsdemo.services;

import com.ijadric.searchmetricsdemo.components.LatestRateHolder;
import com.ijadric.searchmetricsdemo.dto.CurrentRate;
import com.ijadric.searchmetricsdemo.exceptions.NoRateFoundException;
import com.ijadric.searchmetricsdemo.model.Rate;
import com.ijadric.searchmetricsdemo.repositories.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RateServiceImpl implements RateService {
    private final LatestRateHolder latestRateHolder;
    private final RateRepository rateRepository;

    public RateServiceImpl( @Autowired LatestRateHolder latestRateHolder,
                           @Autowired RateRepository rateRepository){
        this.latestRateHolder = latestRateHolder;
        this.rateRepository = rateRepository;
    }

    @Override
    public List<Rate> getAllRatesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)){
            throw new IllegalArgumentException("Start date is after end date");
        }
        var optionalRates =  rateRepository.findRatesByRetrievedAtBetween(startDate, endDate);
        if (optionalRates.isEmpty()){
            throw new NoRateFoundException();
        }
        return optionalRates.get();
    }

    @Override
    public double getLatestRate() {
        var latestPrice = latestRateHolder.getLatestPrice();
        if (latestPrice == null){
            throw new NoRateFoundException();
        }
        return latestPrice;
    }


}
