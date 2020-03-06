package com.ijadric.searchmetricsdemo;


import com.ijadric.searchmetricsdemo.components.LatestRateHolder;
import com.ijadric.searchmetricsdemo.exceptions.NoRateFoundException;
import com.ijadric.searchmetricsdemo.model.Rate;
import com.ijadric.searchmetricsdemo.repositories.RateRepository;
import com.ijadric.searchmetricsdemo.services.RateService;
import com.ijadric.searchmetricsdemo.services.RateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.ijadric.searchmetricsdemo.TestUtils.prepareRates;
import static org.mockito.Mockito.when;


@DataJpaTest
public class RatesServiceUnitTest {

    @Autowired
    TestEntityManager entityManager;

    @MockBean
    RateRepository rateRepository;

    @MockBean
    LatestRateHolder latestRateHolder;



    private RateService rateService;



    @BeforeEach
    void setup(){
        this.rateService = new RateServiceImpl(latestRateHolder,
                                                rateRepository);
    }

    @Test
    void getAllRates_failWhenStartDateIsAfterEndDate(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rateService.getAllRatesBetweenDates(
                        LocalDateTime.of(2020, 3, 1, 22, 55),
                        LocalDateTime.of(2020, 3, 1, 22, 50)
        ));

    }

    @Test
    void getAllRates_failWithNoRatesFound(){
        Assertions.assertThrows(NoRateFoundException.class,
                () -> rateService.getAllRatesBetweenDates(
                        LocalDateTime.of(2020, 3, 1, 22, 50),
                        LocalDateTime.of(2020, 3, 1, 22, 55))
        );
    }

    @Test
    void getLatestRates_failWithNoRateFound(){
        when(latestRateHolder.getLatestPrice()).thenReturn(null);
        Assertions.assertThrows(NoRateFoundException.class,
                () -> rateService.getLatestRate()
        );
    }

    @Test
    void getLatestRates_succeed(){
        when(latestRateHolder.getLatestPrice()).thenReturn(Double.valueOf(5000));
        var latestPrice = rateService.getLatestRate();
        Assertions.assertEquals(latestPrice, 5000);
    }

    @Test
    void getRates_succeed(){
        Optional<List<Rate>> optionalRates = Optional.of(prepareRates());
        when(rateRepository.findRatesByRetrievedAtBetween(
                LocalDateTime.of(2020, 3, 1, 22, 50),
                LocalDateTime.of(2020, 3, 1, 22, 55))
        ).thenReturn(optionalRates);
        var rates = rateService.getAllRatesBetweenDates(
                LocalDateTime.of(2020, 3, 1, 22, 50),
                LocalDateTime.of(2020, 3, 1, 22, 55)
        );
        Assertions.assertEquals(rates.size(), 3);

    }


}
