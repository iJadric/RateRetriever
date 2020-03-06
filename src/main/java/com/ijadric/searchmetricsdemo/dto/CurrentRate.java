package com.ijadric.searchmetricsdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentRate {

    /**
     * "trade_id": 4729088,
     *   "price": "333.99",
     *   "size": "0.193",
     *   "bid": "333.98",
     *   "ask": "333.99",
     *   "volume": "5957.11914015",
     *   "time": "2015-11-14T20:46:03.511254Z"
     */

    @JsonProperty("trade_id")
    private long tradeId;
    private double price;
    private double size;
    private double bid;
    private double ask;
    private double volume;
    private LocalDateTime time;

    public CurrentRate() {
    }

    public long getTradeId() {
        return tradeId;
    }

    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
