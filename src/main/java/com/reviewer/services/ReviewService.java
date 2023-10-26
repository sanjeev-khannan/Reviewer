package com.reviewer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.reviewer.dao.Review;
import com.reviewer.dao.User;
import com.reviewer.repositories.ReviewRepository;

@Service
public class ReviewService {
    
	@Autowired
	private ReviewRepository reviewRepository;

    public boolean createReview(Review review) {

		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
            review.setUserId(user.getUserId());
            reviewRepository.save(review);
			return true;

		} catch (Exception e) {
			System.out.println("ReviewService: Exception while Creating Review");
			throw (e);
		}
	}

    public boolean validatePartialReview(Review review) {
        if(review!=null && review.getReview()!=null && !review.getReview().equals("")){
            return true;
        }
        return false;
    }
}
