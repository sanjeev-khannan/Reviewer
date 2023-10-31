package com.reviewer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.reviewer.dao.User;
import com.reviewer.repositories.UserDetailsRepository;

@Service
public class LoginService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	public User authenticateUser(String email, String password) {

		try {

			User user = userDetailsRepository.findByEmail(email);
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

	public User createUser(User userdetails) {

		try {
			userDetailsRepository.save(userdetails);
			System.out.println("LoginService: Sign Up - success");
			return userdetails;
		} catch (Exception e) {
			System.out.println("LoginService: Exception while Signing Up");
			throw (e);
		}
	}

	public String checkExistingUser(User userDetails) {

		if (userDetailsRepository.existsByEmail(userDetails.getEmail())) {
			return "Email Already Exists";
		} else if (userDetailsRepository.existsByMobileNumber(userDetails.getMobileNumber())) {
			return "MobileNumber Already Exists";
		}

		return "success";
	}

	public boolean validateUserDetails(User user){
		return true;
	}

	public void updateUser(User user) {
		try {
			User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User userFromDB = userDetailsRepository.findByEmail(userDetails.getEmail());

			userFromDB.setFirstName(user.getFirstName());
			userFromDB.setLastName(user.getLastName());
			userFromDB.setAddressLine1(user.getAddressLine1());
			userFromDB.setAddressLine2(user.getAddressLine2());
			userFromDB.setCity(user.getCity());
			userFromDB.setState(user.getState());
			userFromDB.setZipCode(user.getZipCode());

			userDetailsRepository.save(userFromDB);
			System.out.println("LoginService: SaveDetails - success");
		} catch (Exception e) {
			System.out.println("LoginService: Exception while SaveDetails");
			throw (e);
		}
	}

    public void deleteAccount(User user) {
		userDetailsRepository.delete(user);
    }

	public User getUser(Long userId) {
		return userDetailsRepository.findByUserId(userId);
    }
}
