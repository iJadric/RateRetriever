package com.ijadric.searchmetricsdemo.components;

import java.util.concurrent.atomic.AtomicReference;

public class LatestRateHolder {

    private AtomicReference<Double> latestPrice = null;

    public LatestRateHolder() {
    }

    public Double getLatestPrice() {
        return latestPrice.get();
    }

    public void setLatestPrice(double latestPrice) {
        this.latestPrice.set(latestPrice);
    }
}
