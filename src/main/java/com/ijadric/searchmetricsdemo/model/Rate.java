package com.ijadric.searchmetricsdemo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Rate {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
    private long rateId;

    private double price;

    private LocalDateTime retrievedAt;

    public Rate() {
    }

    public long getRateId() {
        return rateId;
    }

    public void setRateId(long rateId) {
        this.rateId = rateId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getRetrievedAt() {
        return retrievedAt;
    }

    public void setRetrievedAt(LocalDateTime retrievedAt) {
        this.retrievedAt = retrievedAt;
    }
}
