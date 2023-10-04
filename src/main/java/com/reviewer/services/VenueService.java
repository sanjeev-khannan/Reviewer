package com.reviewer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reviewer.dao.Venue;
import com.reviewer.repositories.VenueRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class VenueService {
    
    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private EntityManager entityManager;

    public List<Venue> findVenueWithCriteria(String search_phrase){
        

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Venue> criteriaQuery = criteriaBuilder.createQuery(Venue.class);
        Root<Venue> root = criteriaQuery.from(Venue.class);

        Predicate predicate = criteriaBuilder.conjunction(); // Initialize with AND

        predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("venueName")), search_phrase));

        criteriaQuery.where(predicate);

        List<Venue> venues = entityManager.createQuery(criteriaQuery).getResultList();
        System.out.println(venues);
        return venues;
    }

    public List<Venue> getAllVenues(){
        
        List<Venue> venues = venueRepository.findAll();
        return venues;
    }
}
