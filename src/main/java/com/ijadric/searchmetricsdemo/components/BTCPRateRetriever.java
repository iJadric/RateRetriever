package com.ijadric.searchmetricsdemo.components;

import com.ijadric.searchmetricsdemo.dto.CurrentRate;
import com.ijadric.searchmetricsdemo.model.Rate;
import com.ijadric.searchmetricsdemo.repositories.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Component
public class BTCPRateRetriever {

    private static final String BASE_URL="https://api.pro.coinbase.com";
    private static final String ENDPOINT="/products/BTC-USD/ticker";

    private final RestTemplate restTemplate;
    private final LatestRateHolder latestRateHolder;
    private final RateRepository rateRepository;

    public BTCPRateRetriever(@Autowired RestTemplate restTemplate,
                             @Autowired LatestRateHolder latestRateHolder,
                             @Autowired RateRepository rateRepository) {
        this.restTemplate = restTemplate;
        this.latestRateHolder = latestRateHolder;
        this.rateRepository = rateRepository;
    }

    @Scheduled(fixedRateString = "${application.checkperiod:2000}")
    public void retrieveLatestRate(){
        CurrentRate currentRate = this.restTemplate.getForObject(BASE_URL+ENDPOINT, CurrentRate.class);
        if (currentRate == null){
            return;
        }
        var rate = new Rate();
        rate.setPrice(currentRate.getPrice());
        rate.setRetrievedAt(LocalDateTime.now());
        System.out.println("Saving new rate ");
        this.rateRepository.save(rate);
        this.latestRateHolder.setLatestPrice(currentRate.getPrice());

    }


}
