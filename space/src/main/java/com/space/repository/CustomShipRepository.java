package com.space.repository;

import com.space.model.ShipType;

import java.util.Collection;
import java.util.List;

public interface CustomShipRepository<T> {
    List<T> getFilteredShipsFromDb(
            String name,
            String planet,
            ShipType shipType,
            Long after,
            Long before,
            Boolean isUsed,
            Double minSpeed,
            Double maxSpeed,
            Integer minCrewSize,
            Integer maxCrewSize,
            Double minRating,
            Double maxRating,
            Integer pageNumber,
            Integer pageSize
    );

    Integer getCountOfFilteredShipsFromDb(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating);
}
