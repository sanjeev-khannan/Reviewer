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
			String res = loginService.checkExistingUser(user);
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

	@RequestMapping(path = "/account/saveDetails", method = RequestMethod.POST)
	public ResponseEntity<ReviewerResponse> saveDetails(@RequestBody User user) {

		try {
			System.out.println("Entered UserDetails");
			if (loginService.validateUserDetails(user)){
				loginService.updateUser(user);
				return ResponseEntity.ok(new ReviewerResponse(HttpServletResponse.SC_OK, "Update User Success"));
			} else {
				return ResponseEntity.badRequest().body(new ReviewerResponse(HttpServletResponse.SC_OK, "Please check details"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new ReviewerResponse(HttpServletResponse.SC_OK, "Unexpected error while save details"));
		}

	}

	@RequestMapping(path = "/account/deleteaccount", method = RequestMethod.DELETE)
	public ResponseEntity<ReviewerResponse> deleteAccount() {

		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user != null) {
				loginService.deleteAccount(user);
				return ResponseEntity.ok(new ReviewerResponse(HttpServletResponse.SC_OK, "ACCOUNT DELETED"));
			} else {
				return ResponseEntity.badRequest().body(
					new ReviewerResponse(HttpServletResponse.SC_BAD_REQUEST, "UNAUTHORIZED_ACCESS"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(
				new ReviewerResponse(HttpServletResponse.SC_BAD_REQUEST, "Unexpected Error occured account deletion!!"));
		}

	}
}
