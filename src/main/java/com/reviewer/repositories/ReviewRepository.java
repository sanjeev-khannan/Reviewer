package com.reviewer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reviewer.dao.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review , Long> {
    // You can add custom query methods here if needed

    public Review findByReviewId(Long review_id);
	
}