package com.ijadric.searchmetricsdemo.services;

import com.ijadric.searchmetricsdemo.model.Rate;

import java.time.LocalDateTime;
import java.util.List;

public interface RateService {
    List<Rate> getAllRatesBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    double getLatestRate();
}
