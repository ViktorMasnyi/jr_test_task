package com.space.repository;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.hibernate.Criteria;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CustomShipRepositoryImpl implements CustomShipRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Ship> getFilteredShipsFromDb(
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
    ) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Ship> criteria = builder.createQuery(Ship.class);
        Root<Ship> productRoot = criteria.from(Ship.class);

        //Constructing list of parameters
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (name != null) predicates.add(builder.like(productRoot.get("name"), "%" + name + "%"));
        if (planet != null) predicates.add(builder.like(productRoot.get("planet"), "%" + planet + "%"));
        if (shipType != null) predicates.add(builder.equal(productRoot.get("shipType"), shipType));
        if (after != null) predicates.add(builder.greaterThanOrEqualTo(productRoot.get("after"), after));
        if (before != null) predicates.add(builder.lessThanOrEqualTo(productRoot.get("before"), before));
        if (isUsed != null) predicates.add(builder.equal(productRoot.get("isUsed"), isUsed));
        if (minSpeed != null) predicates.add(builder.greaterThanOrEqualTo(productRoot.get("speed"), minSpeed));
        if (maxSpeed != null) predicates.add(builder.lessThanOrEqualTo(productRoot.get("speed"), maxSpeed));
        if (minCrewSize != null) predicates.add(builder.greaterThanOrEqualTo(productRoot.get("crewSize"), minCrewSize));
        if (maxCrewSize != null) predicates.add(builder.lessThanOrEqualTo(productRoot.get("crewSize"), maxCrewSize));
        if (minRating != null) predicates.add(builder.greaterThanOrEqualTo(productRoot.get("rating"), minRating));
        if (maxRating != null) predicates.add(builder.lessThanOrEqualTo(productRoot.get("rating"), maxRating));

        criteria.select(productRoot).where(predicates.toArray(new Predicate[]{}));
        int resultCount = getCountOfFilteredShipsFromDb(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating);
        int safepageSize = pageSize != null && pageSize != 0 ? pageSize : 3;
        int safePageNumber = pageNumber != null ? pageNumber : 0;

        int totalPagesCount = (int) Math.ceil(resultCount / (double) safepageSize);
        System.out.println("totalPagesCount to int: " + totalPagesCount);
        System.out.println("totalPagesCount double: " + (resultCount / (double) safepageSize));
        System.out.println("======================");
        int lastPageSize = resultCount - safepageSize * totalPagesCount;
        System.out.println("");


        TypedQuery<Ship> query = em.createQuery(criteria)
            .setFirstResult(safePageNumber * safepageSize);


        if (totalPagesCount == safePageNumber) {
            query.setMaxResults(lastPageSize);
        } else {
            query.setMaxResults(safepageSize);
        }

        return query.getResultList();

    }

    @Override
    public Integer getCountOfFilteredShipsFromDb(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Ship> criteria = builder.createQuery(Ship.class);
        Root<Ship> productRoot = criteria.from(Ship.class);

        //Constructing list of parameters
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (name != null) predicates.add(builder.like(productRoot.get("name"), "%" + name + "%"));
        if (planet != null) predicates.add(builder.like(productRoot.get("planet"), "%" + planet + "%"));
        if (shipType != null) predicates.add(builder.equal(productRoot.get("shipType"), shipType));
        if (after != null) predicates.add(builder.greaterThanOrEqualTo(productRoot.get("after"), after));
        if (before != null) predicates.add(builder.lessThanOrEqualTo(productRoot.get("before"), before));
        if (isUsed != null) predicates.add(builder.equal(productRoot.get("isUsed"), isUsed));
        if (minSpeed != null) predicates.add(builder.greaterThanOrEqualTo(productRoot.get("speed"), minSpeed));
        if (maxSpeed != null) predicates.add(builder.lessThanOrEqualTo(productRoot.get("speed"), maxSpeed));
        if (minCrewSize != null) predicates.add(builder.greaterThanOrEqualTo(productRoot.get("crewSize"), minCrewSize));
        if (maxCrewSize != null) predicates.add(builder.lessThanOrEqualTo(productRoot.get("crewSize"), maxCrewSize));
        if (minRating != null) predicates.add(builder.greaterThanOrEqualTo(productRoot.get("rating"), minRating));
        if (maxRating != null) predicates.add(builder.lessThanOrEqualTo(productRoot.get("rating"), maxRating));

        criteria.select(productRoot).where(predicates.toArray(new Predicate[]{}));

        return em.createQuery(criteria).getResultList().size();
    }
}
