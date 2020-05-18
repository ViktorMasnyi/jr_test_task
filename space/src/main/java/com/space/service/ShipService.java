package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;

import java.util.Date;
import java.util.List;

public interface ShipService {

    List<Ship> getShipsByCriteria(
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

    List<Ship> sortShips(List<Ship> ships, ShipOrder order);

    Integer getShipsCount(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating);

    double computeRating(Double speed, Boolean used, Date prodDate);

    boolean isShipValid(Ship ship);

    Ship saveShip(Ship ship);

    Ship getShip(Long id);

    Ship updateShip(Ship oldShip, Ship ship);

    void deleteShip(Ship ship);

}
