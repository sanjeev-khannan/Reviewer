package com.reviewer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reviewer.dao.Venue;
import com.reviewer.pojos.SearchQuery;
import com.reviewer.repositories.VenueRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class VenueService {
    
    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private EntityManager entityManager;

    public int[] getPriceRange(String option){
        if(option.equals("o1")){
            return new int[]{0, 20, 1000000, 0};
        }
        else if(option.equals("o2")){
            return new int[]{0, 100, 1000000, 0};
        }
        else if(option.equals("o3")){
            return new int[]{20, 150, 150, 0};
        }
        else if(option.equals("o4")){
            return new int[]{0, 1000000, 1000000, 100};
        }
        else{
            return new int[]{0, 1000000, 1000000, 0};
        }
    }

    public Order getSortingCriteria(String option, CriteriaBuilder criteriaBuilder, Root<Venue> root){
        if(option.equals("o1")){
            return criteriaBuilder.asc(root.get("minPrice"));
        }
        else if(option.equals("o2")){
            return criteriaBuilder.desc(root.get("maxPrice"));
        }
        else if(option.equals("o3")){
            return criteriaBuilder.asc(root.get("rating"));
        }
        else if(option.equals("o4")){
            return criteriaBuilder.desc(root.get("rating"));
        }
        else{
            return criteriaBuilder.asc(root.get("venueName"));
        }
    }
    public List<Venue> findVenueWithCriteria(SearchQuery searchQuery){
        
        String search_phrase = '%' + searchQuery.getSearchPhrase().toLowerCase() + '%';
        
        String price = searchQuery.getPrice();
        int[] price_range = getPriceRange(price);
        int min_price_greater = price_range[0];
        int min_price_lesser = price_range[1];
        int max_price_lesser = price_range[2];
        int max_price_greater = price_range[3];

        int rating = (searchQuery.getRating()!="")?Integer.parseInt(searchQuery.getRating()):0;

        String sorting = searchQuery.getSorting();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Venue> criteriaQuery = criteriaBuilder.createQuery(Venue.class);
        Root<Venue> root = criteriaQuery.from(Venue.class);

        Predicate predicate = criteriaBuilder.conjunction(); // Initialize with AND

        predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("venueName")), search_phrase));
        
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("minPrice"), min_price_greater));
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("minPrice"), min_price_lesser));
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("maxPrice"), max_price_lesser));
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("maxPrice"), max_price_greater));

        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), rating));

        criteriaQuery.where(predicate);
        
        criteriaQuery.orderBy(getSortingCriteria(sorting, criteriaBuilder, root));

        List<Venue> venues = entityManager.createQuery(criteriaQuery).getResultList();
        return venues;
    }

    public List<Venue> getAllVenues(){
        
        List<Venue> venues = venueRepository.findAll();
        return venues;
    }
}
