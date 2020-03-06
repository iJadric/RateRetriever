package com.ijadric.searchmetricsdemo;


import com.ijadric.searchmetricsdemo.repositories.RateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static com.ijadric.searchmetricsdemo.TestUtils.prepareRates;

@DataJpaTest
public class RatesRepositoryUnitTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    RateRepository rateRepository;

    @BeforeEach
    void setup(){
        var rates = prepareRates();
        for (var rate: rates)
            entityManager.persist(rate);
        entityManager.flush();
    }

    @Test
    void testFindAll(){
        var rates = rateRepository.findAll();
        var counter = 0;
        for (var rate : rates){
            counter += 1;
        }
        Assertions.assertEquals(3, counter);
    }

    @Test
    void testFindByDatesAndShouldReturnAllRates(){
        var rates = rateRepository.findRatesByRetrievedAtBetween(
                LocalDateTime.of(2020,3,1,22,52),
                LocalDateTime.of(2020,3,1,22,54));
        Assertions.assertTrue(rates.isPresent());
        Assertions.assertEquals(3, rates.get().size());

        rates = rateRepository.findRatesByRetrievedAtBetween(
                LocalDateTime.of(1970,1,1,0,0),
                LocalDateTime.now());
        Assertions.assertTrue(rates.isPresent());
        Assertions.assertEquals(3, rates.get().size());

    }

    @Test
    void testFindByDatesAndShouldReturnPartialRates(){
        var rates = rateRepository.findRatesByRetrievedAtBetween(
                LocalDateTime.of(2020,3,1,22,53),
                LocalDateTime.of(2020,3,1,22,54));
        Assertions.assertTrue(rates.isPresent());
        Assertions.assertEquals(2, rates.get().size());
    }
}
