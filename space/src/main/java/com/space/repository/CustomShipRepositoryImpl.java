package com.space.repository;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.hibernate.Criteria;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomShipRepositoryImpl implements CustomShipRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Ship> getFilteredShipsFromDb(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {
//        Query q = em.createQuery("select c from Ship c WHERE c.name = :name", Ship.class);
//                q.setParameter("name", name)
//                .getResultList();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Ship> criteria = builder.createQuery(Ship.class);
        Root<Ship> productRoot = criteria.from(Ship.class);

        //Constructing list of parameters
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (name != null) predicates.add(builder.equal(productRoot.get("name"), name));
        if (planet != null) predicates.add(builder.equal(productRoot.get("planet"), planet));
        if (shipType != null) predicates.add(builder.equal(productRoot.get("shipType"), shipType));

        criteria.select(productRoot).where(predicates.toArray(new Predicate[]{}));

    return em.createQuery(criteria).getResultList();

    }
}
