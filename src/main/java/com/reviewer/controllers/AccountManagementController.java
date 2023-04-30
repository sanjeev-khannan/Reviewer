package com.reviewer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reviewer.dao.User;
import com.reviewer.pojos.ReviewerResponse;
import com.reviewer.services.LoginService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AccountManagementController {

	@Autowired
	private LoginService loginService;

	@RequestMapping(path = "/getuserbytoken", method = RequestMethod.POST)
	public ResponseEntity<?> getUser() {

		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user != null) {
				return ResponseEntity.ok(user);
			} else {
				return ResponseEntity.badRequest().body(
					new ReviewerResponse(HttpServletResponse.SC_BAD_REQUEST, "UNAUTHORIZED_ACCESS"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(
				new ReviewerResponse(HttpServletResponse.SC_BAD_REQUEST, "Unexpected Error occured during Login!!"));
		}

	}

	@RequestMapping(path = "/signup", method = RequestMethod.POST)
	public ResponseEntity<String> signUp(@RequestBody User user) {

		try {
			user.setRole("registered_user");
			String res = loginService.validateUserDetails(user);
			if (res.equals("success")) {
				loginService.createUser(user);
				return ResponseEntity.ok("SignUp success");
			} else {
				return ResponseEntity.badRequest().body(res);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Unexpected Error occured during SignUp");
		}

	}
}
