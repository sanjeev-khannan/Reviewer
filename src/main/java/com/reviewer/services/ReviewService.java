package com.reviewer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.reviewer.dao.Reviews;
import com.reviewer.dao.User;
import com.reviewer.repositories.ReviewRepository;

import jakarta.persistence.EntityManager;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private EntityManager entityManager;

	public boolean createReview(Reviews review) {

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

	public boolean validatePartialReview(Reviews review) {
		if (review != null && review.getReview() != null && !review.getReview().equals("")
				&& review.getRating() != null && review.getVenueId() != null) {
			return true;
		}
		System.out.println(review.getRating() + "\n" + review.getReview() + "\n" + review.getVenueId());
		return false;
	}

	public List<Reviews> getReviews(Long venueId) {

		// String sqlQuery = "select c.review_id as reviewId, c.user_id as user_id,
		// c.review as review, c.liked as liked, d.like_count as like_count,
		// d.dislike_count as dislike_count from ( select a.review_id as reviewId,
		// a.user_id as user_id, a.review as review, b.liked as liked from Reviews as a
		// left join ( select x.review_id as reviewId, x.user_id as user_id, x.liked as
		// liked, x.dislike as dislike from ReviewLikes as x where x.user_id=1 ) as b on
		// a.review_id=b.review_id and a.user_id=b.user_id where a.venue_id=1 ) as c
		// left join ( select y.review_id as reviewId, sum(y.liked) as like_count,
		// sum(y.dislike) as dislike_count from ReviewLikes as y group by y.review_id )
		// as d on c.review_id=d.review_id";

		// Query query = entityManager.createQuery(sqlQuery, JoinReviewsAndLikes.class);

		return reviewRepository.findByVenueId(venueId);
	}
}
