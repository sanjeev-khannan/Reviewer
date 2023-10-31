package com.reviewer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reviewer.dao.Reviews;
import com.reviewer.dao.User;
import com.reviewer.pojos.ReviewerResponse;
import com.reviewer.services.ReviewService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ReviewController {

	@Autowired
	public ReviewService reviewService;

	@RequestMapping(path = "/createreview", method = RequestMethod.POST)
	public ResponseEntity<?> createReview(@RequestBody Reviews review) {

		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (user != null) {
				review.setUserId(user.getUserId());
				if (reviewService.validatePartialReview(review)) {
					reviewService.createReview(review);
					return ResponseEntity.ok(new ReviewerResponse(HttpServletResponse.SC_OK, "Review_CREATED"));
				}
				return ResponseEntity.badRequest()
						.body(new ReviewerResponse(HttpServletResponse.SC_BAD_REQUEST, "INVALID_Review"));
			} else {
				return ResponseEntity.badRequest().body(new ReviewerResponse(HttpServletResponse.SC_UNAUTHORIZED,
						"UNAUTHORIZED_ACCESS"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new ReviewerResponse(HttpServletResponse.SC_CONFLICT,
					"Unexpected Error occured while getting Review!"));
		}
	}

	@RequestMapping(path = "/getreviews", method = RequestMethod.GET)
	public ResponseEntity<?> createReview(@RequestParam Long venue_id) {

		try {
			return ResponseEntity.ok(reviewService.getReviews(venue_id));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new ReviewerResponse(HttpServletResponse.SC_CONFLICT,
					"Unexpected Error occured while getting Review!"));
		}
	}

}
