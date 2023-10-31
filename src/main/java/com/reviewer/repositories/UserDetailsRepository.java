package com.reviewer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reviewer.dao.User;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);

	public User findByUserId(Long userId);

	public boolean existsByEmail(String email);

	public boolean existsByMobileNumber(String mobileNumber);

}
