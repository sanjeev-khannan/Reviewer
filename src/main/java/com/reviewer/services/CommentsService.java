package com.reviewer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.reviewer.dao.Comment;
import com.reviewer.dao.User;
import com.reviewer.repositories.CommentRepository;

@Service
public class CommentsService {
    
	@Autowired
	private CommentRepository commentRepository;

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
        if(comment!=null && comment.getComment()!=null && !comment.getComment().equals("")){
            return true;
        }
        return false;
    }
}
