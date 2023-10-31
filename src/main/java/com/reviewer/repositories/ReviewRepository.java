package com.reviewer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reviewer.dao.Reviews;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Long> {
    // You can add custom query methods here if needed

    public Reviews findByReviewId(Long review_id);

    public List<Reviews> findByVenueId(Long venue_id);

}