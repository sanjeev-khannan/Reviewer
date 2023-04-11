package com.reviewer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reviewer.pojos.User;
import com.reviewer.repositories.UserDetailsRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	User user = this.userDetailsRepository.findByEmail(username);
    	if (user==null) {
    		throw new UsernameNotFoundException("Invalid user");
    	}
        return user;
    }
}
