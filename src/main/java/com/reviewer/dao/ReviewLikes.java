package com.reviewer.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviewlikes")
public class ReviewLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_likes_id")
    public long reviewLikesId;

    @Column(name = "review_id")
    public long reviewId;

    @Column(name = "user_id")
    public long userId;

    @Column(name = "dislike")
    public int dislike;

    @Column(name = "liked")
    public int liked;

    public long getReviewLikesId() {
        return reviewLikesId;
    }

    public void setReviewLikesId(long reviewLikesId) {
        this.reviewLikesId = reviewLikesId;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long review_id) {
        this.reviewId = review_id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long user_id) {
        this.userId = user_id;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

}
