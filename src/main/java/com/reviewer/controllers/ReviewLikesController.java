package com.reviewer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.reviewer.dao.ReviewLikes;
import com.reviewer.dao.User;
import com.reviewer.pojos.ReviewerResponse;
import com.reviewer.services.ReviewLikesService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ReviewLikesController {

    @Autowired
    public ReviewLikesService reviewLikesService;

    @RequestMapping(path = "/createreviewlike", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewLike(@RequestBody ReviewLikes reviewLikes) {

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (user != null) {
                reviewLikes.setUserId(user.getUserId());
                reviewLikesService.createReviewLikes(reviewLikes);
                return ResponseEntity.ok(new ReviewerResponse(HttpServletResponse.SC_OK, "ReviewLikes_CREATED"));
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
}
