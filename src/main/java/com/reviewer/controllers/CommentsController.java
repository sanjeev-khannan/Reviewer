package com.reviewer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reviewer.dao.Comment;
import com.reviewer.dao.User;
import com.reviewer.pojos.ReviewerResponse;
import com.reviewer.services.CommentsService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class CommentsController {

	@Autowired
	public CommentsService commentsService;

	@RequestMapping(path = "/createcomment", method = RequestMethod.POST)
	public ResponseEntity<?> createReview(@RequestBody Comment comment) {

		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (user != null) {
				if (commentsService.validatePartialComment(comment)) {
					comment.setUserId(user.getUserId());
					commentsService.createComment(comment);
					return ResponseEntity.ok(new ReviewerResponse(HttpServletResponse.SC_OK, "Comment_CREATED"));
				}
				return ResponseEntity.badRequest()
						.body(new ReviewerResponse(HttpServletResponse.SC_BAD_REQUEST, "INVALID_Comment"));
			} else {
				return ResponseEntity.badRequest().body(new ReviewerResponse(HttpServletResponse.SC_UNAUTHORIZED,
						"UNAUTHORIZED_ACCESS"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new ReviewerResponse(HttpServletResponse.SC_CONFLICT,
					"Unexpected Error occured while adding Comment!"));
		}
	}

	@RequestMapping(path = "/getcomments", method = RequestMethod.GET)
	public ResponseEntity<?> createReview(@RequestParam Long review_id) {

		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// review.setUserId(user.getUserId());
			// if(reviewService.validatePartialReview(review)){
			return ResponseEntity.ok(commentsService.getCommentsForReviews(review_id, user.getUserId()));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new ReviewerResponse(HttpServletResponse.SC_CONFLICT,
					"Unexpected Error occured while getting Comments!"));
		}
	}

}
