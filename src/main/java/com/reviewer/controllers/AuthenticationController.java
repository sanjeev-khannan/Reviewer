package com.reviewer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reviewer.dao.User;
import com.reviewer.filters.JwtUtils;
import com.reviewer.pojos.JwtRequest;
import com.reviewer.pojos.JwtResponse;
import com.reviewer.pojos.ReviewerResponse;
import com.reviewer.services.UserDetailsServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		try{
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		
		final String token = JwtUtils.generateToken(authenticationRequest.getUsername());
		
		return ResponseEntity.ok(new JwtResponse(token));
		}
		catch(Exception bce){
			return ResponseEntity.badRequest().body(
				new ReviewerResponse(HttpServletResponse.SC_UNAUTHORIZED, bce.getMessage()));
		}
	}

	private void authenticate(String username, String password) throws Exception {

		User user = userDetailsService.loadUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("USER_NOT_FOUND");
		} else if (!user.getPassword().equals(password)) {
			throw new BadCredentialsException("INVALID_CREDENTIALS");
		}

	}
}
