package com.reviewer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.reviewer.dao.Comment;
import com.reviewer.dao.Reviews;
import com.reviewer.dao.User;
import com.reviewer.pojos.CommentsResult;
import com.reviewer.pojos.ReviewCounts;
import com.reviewer.repositories.CommentRepository;
import com.reviewer.repositories.ReviewRepository;

@Service
public class CommentsService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ReviewLikesService reviewLikesService;

	@Autowired
	private LoginService loginService;

	public boolean createComment(Comment comment) {

		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			comment.setUserId(user.getUserId());
			commentRepository.save(comment);
			return true;

		} catch (Exception e) {
			System.out.println("CommentService: Exception while Creating Comment");
			throw (e);
		}
	}

	public boolean validatePartialComment(Comment comment) {
		if (comment != null && comment.getComment() != null && !comment.getComment().equals("")) {
			return true;
		}
		return false;
	}

	public CommentsResult getCommentsForReviews(Long review_id, Long userId) {

		CommentsResult commentsResult = new CommentsResult();
		Reviews review = reviewRepository.findByReviewId(review_id);
		ReviewCounts reviewCounts = reviewLikesService.getLikeAndDislikeCounts().get(review_id);
		List<Comment> comments = commentRepository.findByReviewId(review_id);
		User user = loginService.getUser(review.getUserId());

		if (userId != null) {
			commentsResult.setIsUserLiked(reviewLikesService.isUserLiked(review_id, userId));

		} else {
			commentsResult.setIsUserLiked(0);
		}

		if (reviewCounts == null) {
			commentsResult.setLike_count(0);
			commentsResult.setDislike_count(0);
		} else {
			commentsResult.setLike_count(reviewCounts.getLikes());
			commentsResult.setDislike_count(reviewCounts.getDislikes());
		}

		commentsResult.setReview_id(review_id);
		commentsResult.setReview(review.getReview());
		commentsResult.setVenue_id(review.getVenueId());
		commentsResult.setComments(comments);
		commentsResult.setUser_name(user.getFirstName());

		return commentsResult;
	}
}
