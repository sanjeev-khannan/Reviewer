package com.reviewer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@RequestMapping(path="/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody String email, @RequestBody String password) {
		
	
		
		return ResponseEntity.ok("login_success");
		
	}
}
