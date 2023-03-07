package com.reviewer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reviewer.pojos.UserDetails;
import com.reviewer.repositories.UserDetailsRepository;

@Service
public class LoginService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	public UserDetails authenticateUser(String email, String password) {

		try {

			UserDetails user = userDetailsRepository.findByEmail(email);
			if (user != null && user.getPassword().equals(password)) {
				System.out.println("Logged In User:" + user.getFirstName());
				return user;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("Unexpected Exception occured in LoginService" + e.getStackTrace());
			throw (e);
		}
	}

	public UserDetails createUser(UserDetails userdetails) {

		try {
			userDetailsRepository.save(userdetails);
			System.out.println("LoginService: Sign Up - success");
			return userdetails;
		} catch (Exception e) {
			System.out.println("LoginService: Exception while Signing Up");
			throw (e);
		}
	}

	public String validateUserDetails(UserDetails userDetails) {

		if (userDetailsRepository.existsByEmail(userDetails.getEmail())) {
			return "Email Already Exists";
		} else if (userDetailsRepository.existsByMobileNumber(userDetails.getMobileNumber())) {
			return "MobileNumber Already Exists";
		}

		return "success";
	}

}
