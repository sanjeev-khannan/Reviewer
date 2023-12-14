package com.reviewer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reviewer.dao.ReviewLikes;

@Repository
public interface ReviewLikesRepository extends JpaRepository<ReviewLikes, Long> {

    public ReviewLikes findByReviewId(Long reviewId);
}
