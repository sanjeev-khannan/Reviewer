package com.reviewer.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.reviewer.dao.ReviewLikes;
import com.reviewer.dao.User;
import com.reviewer.pojos.ReviewCounts;
import com.reviewer.repositories.ReviewLikesRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class ReviewLikesService {

    @Autowired
    private ReviewLikesRepository reviewLikesRepository;

    @Autowired
    private EntityManager entityManager;

    public boolean createReviewLikes(ReviewLikes reviewLikes) {

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            reviewLikes.setUserId(user.getUserId());
            List<ReviewLikes> reviewsLiked = getUserLikedRows(reviewLikes.getReviewId(), reviewLikes.getUserId());
            if (reviewsLiked != null && !reviewsLiked.isEmpty()) {
                reviewLikesRepository.delete(reviewsLiked.get(0));
            }
            reviewLikesRepository.save(reviewLikes);
            return true;

        } catch (Exception e) {
            System.out.println("CommentService: Exception while Creating Comment");
            throw (e);
        }
    }

    public Map<Long, ReviewCounts> getLikeAndDislikeCounts() {

        List<ReviewLikes> reviewLikes = reviewLikesRepository.findAll();
        Map<Long, ReviewCounts> countsMap = new HashMap<>();

        for (ReviewLikes review : reviewLikes) {
            Long reviewId = review.getReviewId();
            int liked = review.getLiked();
            int dislike = review.getDislike();

            ReviewCounts counts = countsMap.get(reviewId);
            if (counts == null) {
                counts = new ReviewCounts();
            }

            if (liked == 1) {
                counts.incrementLikes();
            } else if (dislike == 1) {
                counts.incrementDislikes();
            }

            countsMap.put(reviewId, counts);
        }

        return countsMap;
    }

    public int isUserLiked(Long reviewId, Long userId) {

        List<ReviewLikes> reviewLikes = getUserLikedRows(reviewId, userId);
        if (reviewLikes != null && !reviewLikes.isEmpty()) {
            ReviewLikes rLike = reviewLikes.get(0);
            if (rLike.getLiked() == 1) {
                return 1;
            } else if (rLike.getDislike() == 1) {
                return 2;
            }
        }
        return 0;
    }

    public List<ReviewLikes> getUserLikedRows(Long reviewId, Long userId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReviewLikes> criteriaQuery = criteriaBuilder.createQuery(ReviewLikes.class);
        Root<ReviewLikes> root = criteriaQuery.from(ReviewLikes.class);

        Predicate predicate = criteriaBuilder.conjunction();

        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("userId"), userId));

        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("reviewId"), reviewId));

        criteriaQuery.where(predicate);

        List<ReviewLikes> reviewLikes = entityManager.createQuery(criteriaQuery).getResultList();
        return reviewLikes;
    }
}
