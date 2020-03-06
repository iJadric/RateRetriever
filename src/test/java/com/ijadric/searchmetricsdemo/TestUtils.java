package com.ijadric.searchmetricsdemo;

import com.ijadric.searchmetricsdemo.model.Rate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static List<Rate> prepareRates(){
        var rates = new ArrayList<Rate>();
        var rate1 = new Rate();
        rate1.setPrice(3000);
        rate1.setRetrievedAt(LocalDateTime.of(2020,3,1,22,53));
        var rate2 = new Rate();
        rate2.setPrice(2989);
        rate2.setRetrievedAt(LocalDateTime.of(2020,3,1,22,52));
        var rate3 = new Rate();
        rate3.setPrice(3002);
        rate3.setRetrievedAt(LocalDateTime.of(2020, 3, 1, 22, 54));
        rates.add(rate1);
        rates.add(rate2);
        rates.add(rate3);
        return rates;
    }
}
