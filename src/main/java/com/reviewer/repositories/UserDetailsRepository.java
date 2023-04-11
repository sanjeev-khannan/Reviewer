package com.reviewer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reviewer.pojos.User;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);

	public boolean existsByEmail(String email);

	public boolean existsByMobileNumber(String mobileNumber);

}
