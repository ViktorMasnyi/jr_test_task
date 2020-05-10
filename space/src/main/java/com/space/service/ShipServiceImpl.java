package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

//    @Override
//    public List<Ship> getPage(List<Ship> ships, Integer pageNumber, Integer pageSize) {
//        return null;
//    }
}
