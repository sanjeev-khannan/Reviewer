package com.reviewer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reviewer.dao.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    public Comment findByCommentId(Long comment_id);

    public List<Comment> findByReviewId(Long review_id);
}
