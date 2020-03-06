package com.ijadric.searchmetricsdemo.repositories;

import com.ijadric.searchmetricsdemo.model.Rate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends CrudRepository<Rate, Long> {

    Optional<List<Rate>> findRatesByRetrievedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
