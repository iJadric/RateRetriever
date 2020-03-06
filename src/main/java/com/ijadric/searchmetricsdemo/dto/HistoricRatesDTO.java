package com.ijadric.searchmetricsdemo.dto;

import com.ijadric.searchmetricsdemo.model.Rate;

import java.util.ArrayList;

public class HistoricRatesDTO {
    private Iterable<Rate> rates;

    public HistoricRatesDTO() {
        this.rates = new ArrayList<>();
    }

    public Iterable<Rate> getRates() {
        return rates;
    }

    public void setRates(Iterable<Rate> rates) {
        this.rates = rates;
    }
}
