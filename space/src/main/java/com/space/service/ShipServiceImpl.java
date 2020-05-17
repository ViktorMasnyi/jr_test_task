package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.util.*;

@Service    // marks class as Spring component
@Transactional      // state requirement for transactions
public class ShipServiceImpl implements ShipService {

    private ShipRepository shipRepository;

    @Autowired      // ? what this (Beans)
    public ShipServiceImpl(ShipRepository shipRepository) {
        super();
        this.shipRepository = shipRepository;
    }

    public ShipServiceImpl() {

    }

    @Override
    public List<Ship> getShips(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {
        final List<Ship> list = new ArrayList<>();
        shipRepository.findAll().forEach((ship) -> list.add((Ship) ship));
        return list;
    }

    @Override
    public List<Ship> getShipsByCriteria(
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
            Integer pageSize) {
        return shipRepository.getFilteredShipsFromDb(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating, pageNumber, pageSize);
    }

    @Override
    public List<Ship> sortShips(List<Ship> ships, ShipOrder order) {
        if (order != null) {
            ships.sort((ship1, ship2) -> {
                switch (order) {
                    case ID: return ship1.getId().compareTo(ship2.getId());
                    case SPEED: return ship1.getSpeed().compareTo(ship2.getSpeed());
                    case DATE: return ship1.getProdDate().compareTo(ship2.getProdDate());
                    case RATING: return ship1.getRating().compareTo(ship2.getRating());
                    default: return 0;
                }
            });
    }
        return ships;
    }

    @Override
    public Integer getShipsCount(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {
        return shipRepository.getCountOfFilteredShipsFromDb(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating);
    }

    @Override
    public double computeRating(Double speed, Boolean used, Date prodDate) {
        final int now = 3019;
        final int prodYear = getYearFromDate(prodDate);
        final double k = used ? 0.5 : 1;
        final double rating = 80 * speed * k / (now - prodYear + 1);
        return round(rating);
    }

    @Override
    public boolean isShipValid(Ship ship) {
        return ship != null && isStringValid(ship.getName()) && isStringValid(ship.getPlanet())
                && isProdDateValid(ship.getProdDate())
                && isSpeedValid(ship.getSpeed())
                && isCrewSizeValid(ship.getCrewSize());
    }

    @Override
    public Ship saveShip(Ship ship) {
        return shipRepository.save(ship);
    }


    private boolean isStringValid(String value) {
        final int maxStringLength = 50;
        return value != null && !value.isEmpty() && value.length() <= maxStringLength;
    }

    private boolean isProdDateValid(Date prodDate) {
        final Date startProd = getDateForYear(2800);
        final Date endProd = getDateForYear(3019);
        return prodDate != null && prodDate.after(startProd) && prodDate.before(endProd);
    }

    private Date getDateForYear(int year) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    private int getYearFromDate(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    private boolean isCrewSizeValid(Integer crewSize) {
        final int minCrewSize = 1;
        final int maxCrewSize = 9999;
        return crewSize != null && crewSize.compareTo(minCrewSize) >= 0 && crewSize.compareTo(maxCrewSize) <= 0;
    }

    private boolean isSpeedValid(Double speed) {
        final double minSpeed = 0.01;
        final double maxSpeed = 0.99;
        return speed != null && speed.compareTo(minSpeed) >= 0 && speed.compareTo(maxSpeed) <= 0;
    }

    private double round(double value) {
        return Math.round(value * 100) / 100D;
    }
}
