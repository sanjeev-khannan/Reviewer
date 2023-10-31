package com.reviewer.pojos;

import java.util.List;

import com.reviewer.dao.Comment;

public class CommentsResult {

    public Long review_id;
    public Long venue_id;
    public String user_name;
    public String review;
    public int like_count;
    public int dislike_count;
    public int isUserLiked;
    public List<Comment> comments;

    public Long getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(Long venue_id) {
        this.venue_id = venue_id;
    }

    public Long getReview_id() {
        return review_id;
    }

    public void setReview_id(Long review_id) {
        this.review_id = review_id;
    }

    public int getIsUserLiked() {
        return isUserLiked;
    }

    public void setIsUserLiked(int isUserLiked) {
        this.isUserLiked = isUserLiked;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getDislike_count() {
        return dislike_count;
    }

    public void setDislike_count(int dislike_count) {
        this.dislike_count = dislike_count;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
