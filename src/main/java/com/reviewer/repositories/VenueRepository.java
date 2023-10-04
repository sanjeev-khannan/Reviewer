package com.reviewer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reviewer.dao.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {
    // You can add custom query methods here if needed

    public Venue findByVenueId(Long venue_id);
	
}
